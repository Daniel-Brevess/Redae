package br.com.redae.auth.entity;

import br.com.redae.identity.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "email_verification_token")
public class EmailVerificationToken {
  @Id private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "usuario_id", nullable = false)
  private User user;

  @Column(name = "codigo_hash", nullable = false, length = 64)
  private String codeHash;

  @Column(name = "expira_em", nullable = false)
  private Instant expiresAt;

  @Column(name = "tentativas", nullable = false)
  private int attempts;

  @Column(name = "usado_em")
  private Instant usedAt;

  @Column(name = "criado_em", nullable = false, updatable = false)
  private Instant createdAt;

  public EmailVerificationToken(User user, String codeHash, Instant expiresAt) {
    this.id = UUID.randomUUID();
    this.user = user;
    this.codeHash = codeHash;
    this.expiresAt = expiresAt;
  }

  protected EmailVerificationToken() {}

  public User getUser() {
    return user;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public boolean isUsable(Instant now) {
    return usedAt == null && expiresAt.isAfter(now) && attempts < 5;
  }

  public boolean matches(String hash) {
    return codeHash.equals(hash);
  }

  public void registerAttempt() {
    attempts++;
  }

  public void markUsed() {
    usedAt = Instant.now();
  }

  @PrePersist
  void onCreate() {
    createdAt = Instant.now();
  }
}
