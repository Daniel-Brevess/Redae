package br.com.redae.evaluation.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.redae.evaluation.entity.CompetencyScore;
import br.com.redae.evaluation.entity.Evaluation;
import br.com.redae.evaluation.entity.EvaluationType;
import br.com.redae.evaluation.entity.FeedbackItem;
import br.com.redae.user.entity.User;
import org.junit.jupiter.api.Test;

class EvaluationResponseTest {
  @Test
  void diagnosticResponseContainsOnlyBriefHighlights() {
    Evaluation evaluation = evaluation(EvaluationType.DIAGNOSTICO);

    EvaluationResponse response = EvaluationResponse.from(evaluation);

    assertEquals(EvaluationType.DIAGNOSTICO, response.type());
    assertEquals(1, response.competencies().get(0).highlights().size());
    assertEquals("Problema relevante", response.competencies().get(0).highlights().get(0));
    assertTrue(response.competencies().get(0).feedbackItems().isEmpty());
  }

  @Test
  void completeResponseContainsDetailedFeedback() {
    Evaluation evaluation = evaluation(EvaluationType.COMPLETA);

    EvaluationResponse response = EvaluationResponse.from(evaluation);

    assertEquals(EvaluationType.COMPLETA, response.type());
    assertTrue(response.competencies().get(0).highlights().isEmpty());
    assertEquals(1, response.competencies().get(0).feedbackItems().size());
    assertEquals(
        "Trecho da redação", response.competencies().get(0).feedbackItems().get(0).excerpt());
  }

  private Evaluation evaluation(EvaluationType type) {
    Evaluation evaluation =
        new Evaluation(
            new User("Student", "student@example.com", "hash"), "texto ".repeat(20), "Tema", type);
    CompetencyScore competency = new CompetencyScore(evaluation, "C1", 3, 120, "Resumo");
    competency.addFeedback(
        new FeedbackItem(
            competency,
            "Trecho da redação",
            "Problema relevante",
            "Explicação",
            "Como melhorar",
            "Exemplo",
            null));
    evaluation.addCompetencyScore(competency);
    return evaluation;
  }
}
