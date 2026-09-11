package br.com.redae.gateway.service;

import br.com.redae.evaluation.entity.Evaluation;
import br.com.redae.evaluation.service.EvaluationAccessException;
import br.com.redae.gateway.entity.CreditTransaction;
import br.com.redae.gateway.entity.CreditTransactionType;
import br.com.redae.gateway.repository.CreditTransactionRepository;
import br.com.redae.shared.error.ResourceNotFoundException;
import br.com.redae.user.entity.User;
import br.com.redae.user.repository.UserRepository;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditService {
  private static final int EVALUATION_CREDIT_COST = 1;
  private static final String ADMIN_GRANT_REASON = "Concessão manual para tester";

  private final CreditTransactionRepository creditTransactionRepository;
  private final UserRepository userRepository;

  public CreditService(
      CreditTransactionRepository creditTransactionRepository, UserRepository userRepository) {
    this.creditTransactionRepository = creditTransactionRepository;
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public Map<UUID, Long> getBalances(Collection<UUID> userIds) {
    if (userIds.isEmpty()) {
      return Map.of();
    }
    return creditTransactionRepository.findBalancesByUserIds(userIds).stream()
        .collect(
            Collectors.toMap(
                CreditTransactionRepository.BalanceProjection::getUserId,
                CreditTransactionRepository.BalanceProjection::getCredits,
                (first, ignored) -> first));
  }

  @Transactional
  public long grantCredits(UUID userId, User administrator, int quantity) {
    User targetUser =
        userRepository
            .findByIdForUpdate(userId)
            .orElseThrow(() -> new ResourceNotFoundException("O usuário não foi encontrado."));
    creditTransactionRepository.save(
        new CreditTransaction(targetUser, administrator, quantity, ADMIN_GRANT_REASON));
    return creditTransactionRepository.sumBalanceByUserId(targetUser.getId());
  }

  @Transactional
  public void consumeEvaluationCredit(User user, Evaluation evaluation) {
    User lockedUser =
        userRepository
            .findByIdForUpdate(user.getId())
            .orElseThrow(() -> new IllegalStateException("Usuário não encontrado."));
    if (creditTransactionRepository.existsByEvaluationIdAndType(
        evaluation.getId(), CreditTransactionType.CONSUMO)) {
      return;
    }
    if (creditTransactionRepository.sumBalanceByUserId(lockedUser.getId())
        < EVALUATION_CREDIT_COST) {
      throw new EvaluationAccessException();
    }
    creditTransactionRepository.save(
        new CreditTransaction(
            lockedUser, evaluation, CreditTransactionType.CONSUMO, EVALUATION_CREDIT_COST));
  }

  @Transactional
  public void refundEvaluationCredit(UUID evaluationId) {
    var consumption =
        creditTransactionRepository.findByEvaluationIdAndType(
            evaluationId, CreditTransactionType.CONSUMO);
    if (consumption.isEmpty()
        || creditTransactionRepository.existsByEvaluationIdAndType(
            evaluationId, CreditTransactionType.ESTORNO)) {
      return;
    }
    User lockedUser =
        userRepository
            .findByIdForUpdate(consumption.get().getUser().getId())
            .orElseThrow(() -> new IllegalStateException("Usuário não encontrado."));
    creditTransactionRepository.save(
        new CreditTransaction(
            lockedUser,
            consumption.get().getEvaluation(),
            CreditTransactionType.ESTORNO,
            EVALUATION_CREDIT_COST));
  }
}
