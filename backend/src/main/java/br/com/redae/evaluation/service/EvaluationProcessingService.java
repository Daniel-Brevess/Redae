package br.com.redae.evaluation.service;

import br.com.redae.evaluation.entity.CompetencyScore;
import br.com.redae.evaluation.entity.Evaluation;
import br.com.redae.evaluation.entity.EvaluationStatus;
import br.com.redae.evaluation.entity.FeedbackItem;
import br.com.redae.evaluation.repository.EvaluationRepository;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EvaluationProcessingService {
  private static final Logger log = LoggerFactory.getLogger(EvaluationProcessingService.class);
  private final EvaluationRepository evaluationRepository;
  private final EvaluationAnalyzer evaluationAnalyzer;

  public EvaluationProcessingService(
      EvaluationRepository evaluationRepository, EvaluationAnalyzer evaluationAnalyzer) {
    this.evaluationRepository = evaluationRepository;
    this.evaluationAnalyzer = evaluationAnalyzer;
  }

  @Transactional
  public void process(UUID evaluationId) {
    Evaluation evaluation = evaluationRepository.findById(evaluationId).orElse(null);
    if (evaluation == null || evaluation.getStatus() != EvaluationStatus.PROCESSANDO) return;

    try {
      EvaluationAnalysis analysis = evaluationAnalyzer.analyze(evaluation);
      int finalScore = 0;
      for (var competency : analysis.competencies()) {
        int points = competency.level() * 40;
        finalScore += points;
        var score =
            new CompetencyScore(
                evaluation, competency.code(), competency.level(), points, competency.summary());
        for (var feedback : competency.feedbackItems()) {
          score.addFeedback(
              new FeedbackItem(
                  score,
                  feedback.excerpt(),
                  feedback.problem(),
                  feedback.explanation(),
                  feedback.howToImprove(),
                  feedback.example(),
                  feedback.limitation()));
        }
        evaluation.addCompetencyScore(score);
      }
      evaluation.complete(finalScore, evaluationAnalyzer.modelName());
      evaluationRepository.save(evaluation);
    } catch (RuntimeException exception) {
      Throwable cause = exception;
      while (cause.getCause() != null) {
        cause = cause.getCause();
      }
      log.error("Falha ao processar avaliação {}", evaluationId, exception);
      evaluation.fail(cause.getMessage());
      evaluationRepository.save(evaluation);
    }
  }
}
