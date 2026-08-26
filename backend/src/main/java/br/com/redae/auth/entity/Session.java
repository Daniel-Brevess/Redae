package br.com.redae.auth.entity;

import br.com.redae.identity.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sessao")
public class Session {
  @Id
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "usuario_id", nullable = false)
  private User user;

  @Column(name = "refresh_token_hash", nullable = false, unique = true, length = 255)
  private String refreshTokenHash;

  @Column(name = "expires_at", nullable = false)
  private Instant expiresAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private SessionStatus status;

  @Column(name = "revoked_at")
  private Instant revokedAt;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "last_used_at")
  private Instant lastUsedAt;

  public Session(User user, String refreshTokenHash, Instant expiresAt) {
    this.id = UUID.randomUUID();
    this.user = user;
    this.refreshTokenHash = refreshTokenHash;
    this.expiresAt = expiresAt;
    this.status = SessionStatus.ACTIVE;
  }

  protected Session() {}

  public UUID getId() { return id; }
  public User getUser() { return user; }

  @PrePersist
  void onCreate() {
    createdAt = Instant.now();
  }

  public boolean isActive(Instant now) {
    return status == SessionStatus.ACTIVE && revokedAt == null && expiresAt.isAfter(now);
  }

  public void markUsed() {
    lastUsedAt = Instant.now();
  }

  public void revoke() {
    status = SessionStatus.REVOKED;
    revokedAt = Instant.now();
  }
}
