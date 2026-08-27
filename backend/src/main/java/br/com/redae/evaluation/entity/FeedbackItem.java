package br.com.redae.evaluation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "feedback_item")
public class FeedbackItem {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "nota_competencia_id", nullable = false)
  private CompetencyScore competencyScore;

  @Column(name = "trecho", columnDefinition = "TEXT")
  private String excerpt;

  @Column(name = "problema", nullable = false, columnDefinition = "TEXT")
  private String problem;

  @Column(name = "explicacao", nullable = false, columnDefinition = "TEXT")
  private String explanation;

  @Column(name = "como_melhorar", nullable = false, columnDefinition = "TEXT")
  private String howToImprove;

  @Column(name = "exemplo", columnDefinition = "TEXT")
  private String example;

  @Column(name = "limitacao", columnDefinition = "TEXT")
  private String limitation;

  protected FeedbackItem() {}

  public FeedbackItem(
      CompetencyScore competencyScore,
      String excerpt,
      String problem,
      String explanation,
      String howToImprove,
      String example,
      String limitation) {
    this.competencyScore = competencyScore;
    this.excerpt = excerpt;
    this.problem = problem;
    this.explanation = explanation;
    this.howToImprove = howToImprove;
    this.example = example;
    this.limitation = limitation;
  }

  public String getExcerpt() {
    return excerpt;
  }

  public String getProblem() {
    return problem;
  }

  public String getExplanation() {
    return explanation;
  }

  public String getHowToImprove() {
    return howToImprove;
  }

  public String getLimitation() {
    return limitation;
  }

  public String getExample() {
    return example;
  }
}
