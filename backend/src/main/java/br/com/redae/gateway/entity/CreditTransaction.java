package br.com.redae.gateway.entity;

import br.com.redae.evaluation.entity.Evaluation;
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
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transacao_credito")
public class CreditTransaction {
  @Id private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "usuario_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_transaction_id")
  private PaymentTransaction paymentTransaction;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "avaliacao_id")
  private Evaluation evaluation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "administrador_id")
  private User administrator;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo", nullable = false, length = 20)
  private CreditTransactionType type;

  @Column(name = "quantidade", nullable = false)
  private int quantity;

  @Column(name = "motivo")
  private String reason;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  protected CreditTransaction() {}

  public CreditTransaction(User user, PaymentTransaction paymentTransaction, int quantity) {
    this.id = UUID.randomUUID();
    this.user = user;
    this.paymentTransaction = paymentTransaction;
    this.type = CreditTransactionType.COMPRA;
    this.quantity = quantity;
  }

  public CreditTransaction(
      User user, Evaluation evaluation, CreditTransactionType type, int quantity) {
    this.id = UUID.randomUUID();
    this.user = user;
    this.evaluation = evaluation;
    this.type = type;
    this.quantity = quantity;
  }

  public CreditTransaction(User user, User administrator, int quantity, String reason) {
    this.id = UUID.randomUUID();
    this.user = user;
    this.administrator = administrator;
    this.type = CreditTransactionType.CONCESSAO;
    this.quantity = quantity;
    this.reason = reason;
  }

  public User getUser() {
    return user;
  }

  public CreditTransactionType getType() {
    return type;
  }

  public Evaluation getEvaluation() {
    return evaluation;
  }

  public int getQuantity() {
    return quantity;
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
