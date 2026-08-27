package br.com.redae.evaluation.service;

import br.com.redae.evaluation.dto.CreateEvaluationRequest;
import br.com.redae.evaluation.entity.Evaluation;
import br.com.redae.evaluation.repository.EvaluationRepository;
import br.com.redae.identity.entity.User;
import br.com.redae.shared.error.ResourceNotFoundException;
import java.util.UUID;
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
    Evaluation evaluation =
        evaluationRepository.save(
            new Evaluation(user, request.text().trim(), request.theme().trim()));
    evaluation.startProcessing();
    return evaluationRepository.save(evaluation);
  }

  @Transactional(readOnly = true)
  public Evaluation findOwnedEvaluation(User user, UUID evaluationId) {
    return evaluationRepository
        .findByIdAndUserId(evaluationId, user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("A avaliação não foi encontrada."));
  }
}
