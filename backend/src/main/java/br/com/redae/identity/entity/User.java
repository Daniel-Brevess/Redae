package br.com.redae.identity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class User {
  @Id
  private UUID id;

  @Column(name = "nome", nullable = false, length = 160)
  private String name;

  @Column(name = "email", nullable = false, unique = true, length = 320)
  private String email;

  @Column(name = "senha_hash", nullable = false, length = 255)
  private String passwordHash;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_usuario", nullable = false, length = 20)
  private UserRole role;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  public User(String name, String email, String passwordHash) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
    this.role = UserRole.STUDENT;
  }

  protected User() {}

  public UUID getId() { return id; }
  public String getName() { return name; }
  public String getEmail() { return email; }
  public String getPasswordHash() { return passwordHash; }
  public UserRole getRole() { return role; }

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
