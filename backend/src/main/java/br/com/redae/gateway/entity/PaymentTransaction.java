package br.com.redae.gateway.entity;

import br.com.redae.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment_transaction")
public class PaymentTransaction {
  @Id private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "usuario_id", nullable = false)
  private User user;

  @Column(name = "referencia_externa", unique = true, length = 255)
  private String externalReference;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private PaymentTransactionStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "provider", nullable = false, length = 30)
  private PaymentProvider provider;

  @Column(name = "quantidade_creditos", nullable = false)
  private int creditAmount;

  @Column(name = "bonus_creditos", nullable = false)
  private int bonusCredits;

  @Column(name = "creditos_totais", nullable = false)
  private int totalCredits;

  @Column(name = "valor", nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  @Column(name = "moeda", nullable = false, length = 3)
  private String currency;

  @Column(name = "paga_em")
  private Instant paidAt;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  protected PaymentTransaction() {}

  public PaymentTransaction(User user, int creditAmount, BigDecimal amount) {
    this.id = UUID.randomUUID();
    this.user = user;
    this.creditAmount = creditAmount;
    this.bonusCredits = 0;
    this.totalCredits = creditAmount;
    this.amount = amount;
    this.currency = "BRL";
    this.status = PaymentTransactionStatus.CRIADA;
    this.provider = PaymentProvider.MERCADO_PAGO;
  }

  public UUID getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public String getExternalReference() {
    return externalReference;
  }

  public PaymentTransactionStatus getStatus() {
    return status;
  }

  public PaymentProvider getProvider() {
    return provider;
  }

  public int getTotalCredits() {
    return totalCredits;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Instant getPaidAt() {
    return paidAt;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void markPending(String externalReference) {
    this.externalReference = externalReference;
    this.status = PaymentTransactionStatus.PENDENTE;
  }

  public void markPaid() {
    this.status = PaymentTransactionStatus.PAGA;
    this.paidAt = Instant.now();
  }

  @PrePersist
  void onCreate() {
    Instant now = Instant.now();
    createdAt = now;
    updatedAt = now;
  }

  @PreUpdate
  void onUpdate() {
    updatedAt = Instant.now();
  }
}
