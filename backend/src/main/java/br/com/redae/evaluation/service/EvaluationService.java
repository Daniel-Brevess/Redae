package br.com.redae.evaluation.service;

import br.com.redae.evaluation.dto.CreateEvaluationRequest;
import br.com.redae.evaluation.entity.Evaluation;
import br.com.redae.evaluation.repository.EvaluationRepository;
import br.com.redae.identity.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EvaluationService {
  private final EvaluationRepository evaluationRepository;

  public EvaluationService(EvaluationRepository evaluationRepository) {
    this.evaluationRepository = evaluationRepository;
  }

  @Transactional
  public Evaluation createTypedEvaluation(User user, CreateEvaluationRequest request) {
    return evaluationRepository.save(
        new Evaluation(user, request.text().trim(), request.theme().trim()));
  }
}
