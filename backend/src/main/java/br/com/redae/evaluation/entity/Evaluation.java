package br.com.redae.evaluation.entity;

import br.com.redae.identity.entity.User;
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
@Table(name = "avaliacao")
public class Evaluation {
  @Id private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "usuario_id", nullable = false)
  private User user;

  @Column(name = "texto_confirmado", nullable = false, columnDefinition = "TEXT")
  private String confirmedText;

  @Column(name = "tema", nullable = false, length = 500)
  private String theme;

  @Enumerated(EnumType.STRING)
  @Column(name = "origem", nullable = false, length = 20)
  private EvaluationOrigin origin;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private EvaluationStatus status;

  @Column(name = "nota_final")
  private Integer finalScore;

  @Column(name = "versao", nullable = false, length = 80)
  private String version;

  @Column(name = "modelo_ia", nullable = false, length = 160)
  private String aiModel;

  @Column(name = "gerada_em")
  private Instant generatedAt;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  protected Evaluation() {}

  public Evaluation(User user, String confirmedText, String theme) {
    this.id = UUID.randomUUID();
    this.user = user;
    this.confirmedText = confirmedText;
    this.theme = theme;
    this.origin = EvaluationOrigin.DIGITADA;
    this.status = EvaluationStatus.PENDENTE;
    this.version = "v1";
    this.aiModel = "pending";
  }

  public UUID getId() {
    return id;
  }

  public String getConfirmedText() {
    return confirmedText;
  }

  public String getTheme() {
    return theme;
  }

  public EvaluationOrigin getOrigin() {
    return origin;
  }

  public EvaluationStatus getStatus() {
    return status;
  }

  public void startProcessing() {
    if (status != EvaluationStatus.PENDENTE) {
      throw new IllegalStateException("Somente avaliações pendentes podem iniciar processamento.");
    }
    status = EvaluationStatus.PROCESSANDO;
  }

  public Integer getFinalScore() {
    return finalScore;
  }

  public Instant getCreatedAt() {
    return createdAt;
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
