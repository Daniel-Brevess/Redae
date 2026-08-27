package br.com.redae.evaluation.dto;

import br.com.redae.evaluation.entity.Evaluation;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record EvaluationResponse(
    UUID id,
    String theme,
    String origin,
    String status,
    Integer finalScore,
    String failureReason,
    List<CompetencyResponse> competencies,
    Instant createdAt) {
  public record CompetencyResponse(
      String code, int level, int points, String summary, List<FeedbackResponse> feedbackItems) {}

  public record FeedbackResponse(
      String excerpt,
      String problem,
      String explanation,
      String howToImprove,
      String example,
      String limitation) {}

  public static EvaluationResponse from(Evaluation evaluation) {
    return new EvaluationResponse(
        evaluation.getId(),
        evaluation.getTheme(),
        evaluation.getOrigin().name(),
        evaluation.getStatus().name(),
        evaluation.getFinalScore(),
        evaluation.getFailureReason(),
        evaluation.getCompetencyScores().stream()
            .map(
                competency ->
                    new CompetencyResponse(
                        competency.getCode(),
                        competency.getLevel(),
                        competency.getPoints(),
                        competency.getSummary(),
                        competency.getFeedbackItems().stream()
                            .map(
                                feedback ->
                                    new FeedbackResponse(
                                        feedback.getExcerpt(),
                                        feedback.getProblem(),
                                        feedback.getExplanation(),
                                        feedback.getHowToImprove(),
                                        feedback.getExample(),
                                        feedback.getLimitation()))
                            .toList()))
            .toList(),
        evaluation.getCreatedAt());
  }
}
