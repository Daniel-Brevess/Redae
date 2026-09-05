package br.com.redae.gateway.repository;

import br.com.redae.gateway.entity.PaymentTransaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, UUID> {
  List<PaymentTransaction> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

  Optional<PaymentTransaction> findByExternalReference(String externalReference);
}
