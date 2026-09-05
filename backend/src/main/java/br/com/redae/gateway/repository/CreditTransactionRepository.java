package br.com.redae.gateway.repository;

import br.com.redae.gateway.entity.CreditTransaction;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, UUID> {
  boolean existsByPaymentTransactionId(UUID paymentTransactionId);
}
