package br.com.redae.evaluation.service;

import br.com.redae.evaluation.dto.CreateEvaluationRequest;
import br.com.redae.evaluation.entity.Evaluation;
import br.com.redae.evaluation.entity.EvaluationType;
import br.com.redae.evaluation.repository.EvaluationRepository;
import br.com.redae.shared.error.ResourceNotFoundException;
import br.com.redae.user.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EvaluationService {
  private final EvaluationRepository evaluationRepository;
  private final ApplicationEventPublisher eventPublisher;

  @org.springframework.beans.factory.annotation.Value(
      "${evaluation.require-credit-for-complete:false}")
  private boolean requireCreditForComplete;

  public EvaluationService(
      EvaluationRepository evaluationRepository, ApplicationEventPublisher eventPublisher) {
    this.evaluationRepository = evaluationRepository;
    this.eventPublisher = eventPublisher;
  }

  @Transactional
  public Evaluation createTypedEvaluation(User user, CreateEvaluationRequest request) {
    EvaluationType type = resolveType(user);
    Evaluation evaluation =
        evaluationRepository.save(
            new Evaluation(user, request.text().trim(), request.theme().trim(), type));
    evaluation.startProcessing();
    Evaluation savedEvaluation = evaluationRepository.save(evaluation);
    eventPublisher.publishEvent(new EvaluationCreatedEvent(savedEvaluation.getId()));
    return savedEvaluation;
  }

  private EvaluationType resolveType(User user) {
    if (!evaluationRepository.existsByUserIdAndType(user.getId(), EvaluationType.DIAGNOSTICO)) {
      return EvaluationType.DIAGNOSTICO;
    }
    if (requireCreditForComplete) {
      throw new EvaluationAccessException();
    }
    return EvaluationType.COMPLETA;
  }

  @Transactional(readOnly = true)
  public Evaluation findOwnedEvaluation(User user, UUID evaluationId) {
    return evaluationRepository
        .findByIdAndUserId(evaluationId, user.getId())
        .orElseThrow(() -> new ResourceNotFoundException("A avaliação não foi encontrada."));
  }

  @Transactional(readOnly = true)
  public List<Evaluation> findOwnedEvaluations(User user) {
    return evaluationRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());
  }
}
