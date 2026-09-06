package br.com.redae.gateway.dto;

import br.com.redae.gateway.entity.PaymentTransaction;
import br.com.redae.gateway.entity.PaymentTransactionStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentTransactionResponse(
    UUID id,
    PaymentTransactionStatus status,
    int credits,
    BigDecimal amount,
    String externalReference,
    Instant createdAt) {
  public static PaymentTransactionResponse from(PaymentTransaction transaction) {
    return new PaymentTransactionResponse(
        transaction.getId(),
        transaction.getStatus(),
        transaction.getTotalCredits(),
        transaction.getAmount(),
        transaction.getExternalReference(),
        transaction.getCreatedAt());
  }
}
