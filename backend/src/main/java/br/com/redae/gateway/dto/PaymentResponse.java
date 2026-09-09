package br.com.redae.gateway.dto;

import br.com.redae.gateway.entity.PaymentTransaction;
import br.com.redae.gateway.entity.PaymentTransactionStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentResponse(
    UUID id,
    PaymentTransactionStatus status,
    int credits,
    BigDecimal amount,
    String externalReference,
    String checkoutUrl,
    Instant createdAt) {
  public static PaymentResponse from(
      PaymentTransaction transaction, PaymentCreationResult payment) {
    return new PaymentResponse(
        transaction.getId(),
        transaction.getStatus(),
        transaction.getTotalCredits(),
        transaction.getAmount(),
        transaction.getExternalReference(),
        payment.checkoutUrl(),
        transaction.getCreatedAt());
  }
}
