package br.com.redae.evaluation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.redae.evaluation.dto.CreateEvaluationRequest;
import br.com.redae.evaluation.entity.Evaluation;
import br.com.redae.evaluation.entity.EvaluationStatus;
import br.com.redae.evaluation.entity.EvaluationType;
import br.com.redae.evaluation.repository.EvaluationRepository;
import br.com.redae.identity.entity.User;
import br.com.redae.shared.error.ResourceNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class EvaluationServiceTest {
  @Mock private EvaluationRepository evaluationRepository;
  @Mock private ApplicationEventPublisher eventPublisher;

  @InjectMocks private EvaluationService evaluationService;

  @Test
  void createsTypedEvaluationAndStartsProcessing() {
    User user = new User("Student", "student@example.com", "hash");
    var request = new CreateEvaluationRequest("DIGITADA", "Tema", "a".repeat(80));
    when(evaluationRepository.save(any(Evaluation.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Evaluation evaluation = evaluationService.createTypedEvaluation(user, request);

    assertEquals(EvaluationStatus.PROCESSANDO, evaluation.getStatus());
    assertEquals(EvaluationType.DIAGNOSTICO, evaluation.getType());
    assertEquals("Tema", evaluation.getTheme());
    assertEquals("a".repeat(80), evaluation.getConfirmedText());
    verify(evaluationRepository, times(2)).save(any(Evaluation.class));
  }

  @Test
  void blocksCompleteEvaluationWhenCreditIsRequired() {
    User user = new User("Student", "student@example.com", "hash");
    var request = new CreateEvaluationRequest("DIGITADA", "Tema", "a".repeat(80));
    when(evaluationRepository.existsByUserIdAndType(user.getId(), EvaluationType.DIAGNOSTICO))
        .thenReturn(true);
    ReflectionTestUtils.setField(evaluationService, "requireCreditForComplete", true);

    assertThrows(
        EvaluationAccessException.class,
        () -> evaluationService.createTypedEvaluation(user, request));
  }

  @Test
  void createsCompleteEvaluationWhenCreditCheckIsDisabledForDevelopment() {
    User user = new User("Student", "student@example.com", "hash");
    var request = new CreateEvaluationRequest("DIGITADA", "Tema", "a".repeat(80));
    when(evaluationRepository.existsByUserIdAndType(user.getId(), EvaluationType.DIAGNOSTICO))
        .thenReturn(true);
    when(evaluationRepository.save(any(Evaluation.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Evaluation evaluation = evaluationService.createTypedEvaluation(user, request);

    assertEquals(EvaluationType.COMPLETA, evaluation.getType());
  }

  @Test
  void rejectsEvaluationOwnedByAnotherUserAsNotFound() {
    User user = new User("Student", "student@example.com", "hash");
    UUID evaluationId = UUID.randomUUID();
    when(evaluationRepository.findByIdAndUserId(evaluationId, user.getId()))
        .thenReturn(Optional.empty());

    assertThrows(
        ResourceNotFoundException.class,
        () -> evaluationService.findOwnedEvaluation(user, evaluationId));
  }
}
