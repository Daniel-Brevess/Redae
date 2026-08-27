package br.com.redae.evaluation.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "nota_competencia")
public class CompetencyScore {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "avaliacao_id", nullable = false)
  private Evaluation evaluation;

  @Column(name = "competencia_codigo", nullable = false, length = 2)
  private String code;

  @Column(name = "nivel", nullable = false)
  private int level;

  @Column(name = "pontos", nullable = false)
  private int points;

  @Column(name = "resumo", nullable = false, columnDefinition = "TEXT")
  private String summary;

  @OneToMany(mappedBy = "competencyScore", cascade = CascadeType.ALL, orphanRemoval = true)
  private final Set<FeedbackItem> feedbackItems = new LinkedHashSet<>();

  protected CompetencyScore() {}

  public CompetencyScore(
      Evaluation evaluation, String code, int level, int points, String summary) {
    this.evaluation = evaluation;
    this.code = code;
    this.level = level;
    this.points = points;
    this.summary = summary;
  }

  public String getCode() {
    return code;
  }

  public int getLevel() {
    return level;
  }

  public int getPoints() {
    return points;
  }

  public String getSummary() {
    return summary;
  }

  public List<FeedbackItem> getFeedbackItems() {
    return List.copyOf(feedbackItems);
  }

  public void addFeedback(FeedbackItem feedbackItem) {
    feedbackItems.add(feedbackItem);
  }
}
