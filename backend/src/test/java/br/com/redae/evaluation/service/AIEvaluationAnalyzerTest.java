package br.com.redae.evaluation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import br.com.redae.ai.client.AIClient;
import br.com.redae.evaluation.entity.Evaluation;
import br.com.redae.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AIEvaluationAnalyzerTest {
  @Mock private AIClient aiClient;

  @Test
  void parsesAndValidatesAllCompetencies() {
    Evaluation evaluation = evaluation();
    when(aiClient.modelName()).thenReturn("test-model");
    when(aiClient.generateStructured(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
        .thenReturn(validResponse());

    AIEvaluationAnalyzer analyzer = new AIEvaluationAnalyzer(aiClient, new ObjectMapper());

    EvaluationAnalysis analysis = analyzer.analyze(evaluation);

    assertEquals(5, analysis.competencies().size());
    assertEquals("C1", analysis.competencies().get(0).code());
    assertEquals("test-model", analyzer.modelName());
  }

  @Test
  void keepsFeedbackWithLimitationWhenExcerptDoesNotExistInEssay() {
    Evaluation evaluation = evaluation();
    when(aiClient.generateStructured(
            org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any()))
        .thenReturn(validResponse().replace("trecho", "inexistente"));

    AIEvaluationAnalyzer analyzer = new AIEvaluationAnalyzer(aiClient, new ObjectMapper());

    EvaluationAnalysis analysis = analyzer.analyze(evaluation);

    assertEquals("", analysis.competencies().get(0).feedbackItems().get(0).excerpt());
    assertEquals(
        "A evidência retornada pela IA não foi localizada na redação.",
        analysis.competencies().get(0).feedbackItems().get(0).limitation());
  }

  private Evaluation evaluation() {
    return new Evaluation(
        new User("Student", "student@example.com", "hash"), "trecho válido ".repeat(10), "Tema");
  }

  private String validResponse() {
    return "{\"competencies\":["
        + "{\"code\":\"C1\",\"level\":4,\"summary\":\"Resumo\",\"feedbackItems\":["
        + "{\"excerpt\":\"trecho\",\"problem\":\"Problema\",\"explanation\":\"Explicação\",\"howToImprove\":\"Melhoria\",\"example\":\"Exemplo\"}]},"
        + "{\"code\":\"C2\",\"level\":4,\"summary\":\"Resumo\",\"feedbackItems\":[]},"
        + "{\"code\":\"C3\",\"level\":4,\"summary\":\"Resumo\",\"feedbackItems\":[]},"
        + "{\"code\":\"C4\",\"level\":4,\"summary\":\"Resumo\",\"feedbackItems\":[]},"
        + "{\"code\":\"C5\",\"level\":4,\"summary\":\"Resumo\",\"feedbackItems\":[]}]}";
  }
}
