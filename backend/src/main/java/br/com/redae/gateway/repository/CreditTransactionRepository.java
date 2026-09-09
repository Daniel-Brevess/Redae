package br.com.redae.gateway.repository;

import br.com.redae.gateway.entity.CreditTransaction;
import br.com.redae.gateway.entity.CreditTransactionType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, UUID> {
  boolean existsByPaymentTransactionId(UUID paymentTransactionId);

  boolean existsByEvaluationIdAndType(UUID evaluationId, CreditTransactionType type);

  Optional<CreditTransaction> findByEvaluationIdAndType(
      UUID evaluationId, CreditTransactionType type);

  @Query(
      "select coalesce(sum(transaction.quantity), 0) "
          + "from CreditTransaction transaction "
          + "where transaction.user.id = :userId and transaction.type = :type")
  long sumQuantityByUserIdAndType(
      @Param("userId") UUID userId, @Param("type") CreditTransactionType type);

  @Query(
      "select coalesce(sum(case when transaction.type = br.com.redae.gateway.entity.CreditTransactionType.CONSUMO "
          + "then -transaction.quantity else transaction.quantity end), 0) "
          + "from CreditTransaction transaction where transaction.user.id = :userId")
  long sumBalanceByUserId(@Param("userId") UUID userId);
}
