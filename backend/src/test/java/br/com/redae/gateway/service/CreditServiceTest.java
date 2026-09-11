package br.com.redae.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.redae.evaluation.entity.Evaluation;
import br.com.redae.evaluation.entity.EvaluationType;
import br.com.redae.evaluation.service.EvaluationAccessException;
import br.com.redae.gateway.entity.CreditTransaction;
import br.com.redae.gateway.entity.CreditTransactionType;
import br.com.redae.gateway.repository.CreditTransactionRepository;
import br.com.redae.user.entity.User;
import br.com.redae.user.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {
  @Mock private CreditTransactionRepository creditTransactionRepository;
  @Mock private UserRepository userRepository;

  @InjectMocks private CreditService creditService;

  @Test
  void consumesOneCreditForCompleteEvaluation() {
    User user = new User("Student", "student@example.com", "hash");
    Evaluation evaluation = new Evaluation(user, "a".repeat(80), "Tema", EvaluationType.COMPLETA);
    when(userRepository.findByIdForUpdate(user.getId())).thenReturn(Optional.of(user));
    when(creditTransactionRepository.sumBalanceByUserId(user.getId())).thenReturn(1L);

    creditService.consumeEvaluationCredit(user, evaluation);

    verify(creditTransactionRepository).save(any(CreditTransaction.class));
  }

  @Test
  void blocksCompleteEvaluationWithoutCredit() {
    User user = new User("Student", "student@example.com", "hash");
    Evaluation evaluation = new Evaluation(user, "a".repeat(80), "Tema", EvaluationType.COMPLETA);
    when(userRepository.findByIdForUpdate(user.getId())).thenReturn(Optional.of(user));
    when(creditTransactionRepository.sumBalanceByUserId(user.getId())).thenReturn(0L);

    assertThrows(
        EvaluationAccessException.class,
        () -> creditService.consumeEvaluationCredit(user, evaluation));
  }

  @Test
  void refundsConsumedCreditOnlyOnce() {
    User user = new User("Student", "student@example.com", "hash");
    Evaluation evaluation = new Evaluation(user, "a".repeat(80), "Tema", EvaluationType.COMPLETA);
    CreditTransaction consumption =
        new CreditTransaction(user, evaluation, CreditTransactionType.CONSUMO, 1);
    when(creditTransactionRepository.findByEvaluationIdAndType(
            evaluation.getId(), CreditTransactionType.CONSUMO))
        .thenReturn(Optional.of(consumption));
    when(creditTransactionRepository.existsByEvaluationIdAndType(
            evaluation.getId(), CreditTransactionType.ESTORNO))
        .thenReturn(false);
    when(userRepository.findByIdForUpdate(user.getId())).thenReturn(Optional.of(user));

    creditService.refundEvaluationCredit(evaluation.getId());

    verify(creditTransactionRepository).save(any(CreditTransaction.class));
  }

  @Test
  void returnsBalancesForRequestedUsers() {
    var userId = java.util.UUID.randomUUID();
    var projection = org.mockito.Mockito.mock(CreditTransactionRepository.BalanceProjection.class);
    when(projection.getUserId()).thenReturn(userId);
    when(projection.getCredits()).thenReturn(5L);
    when(creditTransactionRepository.findBalancesByUserIds(List.of(userId)))
        .thenReturn(List.of(projection));

    Map<java.util.UUID, Long> balances = creditService.getBalances(List.of(userId));

    org.junit.jupiter.api.Assertions.assertEquals(Map.of(userId, 5L), balances);
  }

  @Test
  void grantsCreditsToUserAndReturnsUpdatedBalance() {
    User target = new User("Student", "student@example.com", "hash");
    User administrator = new User("Admin", "admin@example.com", "hash");
    when(userRepository.findByIdForUpdate(target.getId())).thenReturn(Optional.of(target));
    when(creditTransactionRepository.sumBalanceByUserId(target.getId())).thenReturn(5L);

    long balance = creditService.grantCredits(target.getId(), administrator, 5);

    assertEquals(5L, balance);
    verify(creditTransactionRepository).save(any(CreditTransaction.class));
  }
}
