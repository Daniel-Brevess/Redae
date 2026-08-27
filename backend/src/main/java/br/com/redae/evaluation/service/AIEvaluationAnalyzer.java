package br.com.redae.evaluation.service;

import br.com.redae.evaluation.entity.Evaluation;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class AIEvaluationAnalyzer implements EvaluationAnalyzer {
  private static final String SCHEMA =
      "{\"type\":\"OBJECT\",\"properties\":{\"competencies\":{\"type\":\"ARRAY\",\"items\":{\"type\":\"OBJECT\",\"properties\":{\"code\":{\"type\":\"STRING\",\"enum\":[\"C1\",\"C2\",\"C3\",\"C4\",\"C5\"]},\"level\":{\"type\":\"INTEGER\"},\"summary\":{\"type\":\"STRING\"},\"feedbackItems\":{\"type\":\"ARRAY\",\"items\":{\"type\":\"OBJECT\",\"properties\":{\"excerpt\":{\"type\":\"STRING\"},\"problem\":{\"type\":\"STRING\"},\"explanation\":{\"type\":\"STRING\"},\"howToImprove\":{\"type\":\"STRING\"},\"example\":{\"type\":\"STRING\"}},\"required\":[\"excerpt\",\"problem\",\"explanation\",\"howToImprove\",\"example\"]}}},\"required\":[\"code\",\"level\",\"summary\",\"feedbackItems\"]}}},\"required\":[\"competencies\"]}";

  private final AIClient aiClient;
  private final ObjectMapper objectMapper;

  public AIEvaluationAnalyzer(AIClient aiClient, ObjectMapper objectMapper) {
    this.aiClient = aiClient;
    this.objectMapper = objectMapper;
  }

  @Override
  public EvaluationAnalysis analyze(Evaluation evaluation) {
    try {
      String response = aiClient.generateStructured(prompt(evaluation), SCHEMA);
      EvaluationAnalysis analysis = objectMapper.readValue(response, EvaluationAnalysis.class);
      return validateAndNormalize(analysis, evaluation.getConfirmedText());
    } catch (Exception exception) {
      throw new IllegalStateException("A resposta da IA não passou pela validação.", exception);
    }
  }

  @Override
  public String modelName() {
    return aiClient.modelName();
  }

  private String prompt(Evaluation evaluation) {
    return "Você é um avaliador especialista em redação dissertativo-argumentativa em português do Brasil. "
        + "Avalie o texto abaixo nas competências C1 a C5. Retorne exclusivamente o JSON conforme o schema. "
        + "Use nível inteiro de 0 a 5. Para cada competência, liste os problemas encontrados. "
        + "O campo excerpt deve copiar literalmente um trecho do texto, preservando acentos, pontuação e palavras; "
        + "não use reticências e, se não houver evidência, use string vazia. "
        + "Preencha todos os campos de feedback. Em example, dê um exemplo curto de reescrita ou melhoria, sem inventar fatos.\n\n"
        + "Tema: "
        + evaluation.getTheme()
        + "\nTexto:\n"
        + evaluation.getConfirmedText();
  }

  private EvaluationAnalysis validateAndNormalize(EvaluationAnalysis analysis, String text) {
    if (analysis == null
        || analysis.competencies() == null
        || analysis.competencies().size() != 5) {
      throw new IllegalArgumentException("A resposta deve conter exatamente C1 a C5.");
    }
    Set<String> codes = new HashSet<>();
    ArrayList<EvaluationAnalysis.CompetencyAnalysis> competencies = new ArrayList<>();
    for (int competencyIndex = 0;
        competencyIndex < analysis.competencies().size();
        competencyIndex++) {
      EvaluationAnalysis.CompetencyAnalysis competency =
          analysis.competencies().get(competencyIndex);
      String competencyPath = "competencies[" + competencyIndex + "]";
      if (competency == null
          || competency.code() == null
          || !Set.of("C1", "C2", "C3", "C4", "C5").contains(competency.code())
          || !codes.add(competency.code())
          || competency.level() < 0
          || competency.level() > 5
          || blank(competency.summary())
          || competency.feedbackItems() == null) {
        throw new IllegalArgumentException(competencyPath + " é inválida.");
      }
      ArrayList<EvaluationAnalysis.FeedbackAnalysis> feedbackItems = new ArrayList<>();
      for (int feedbackIndex = 0;
          feedbackIndex < competency.feedbackItems().size();
          feedbackIndex++) {
        EvaluationAnalysis.FeedbackAnalysis feedback =
            competency.feedbackItems().get(feedbackIndex);
        String feedbackPath = competencyPath + ".feedbackItems[" + feedbackIndex + "]";
        validateFeedback(feedback, feedbackPath);
        if (feedback.excerpt() != null
            && !feedback.excerpt().isBlank()
            && !containsNormalized(text, feedback.excerpt())) {
          feedbackItems.add(
              new EvaluationAnalysis.FeedbackAnalysis(
                  "",
                  feedback.problem(),
                  feedback.explanation(),
                  feedback.howToImprove(),
                  feedback.example(),
                  "A evidência retornada pela IA não foi localizada na redação."));
        } else {
          feedbackItems.add(feedback);
        }
      }
      competencies.add(
          new EvaluationAnalysis.CompetencyAnalysis(
              competency.code(), competency.level(), competency.summary(), feedbackItems));
    }
    return new EvaluationAnalysis(competencies);
  }

  private void validateFeedback(EvaluationAnalysis.FeedbackAnalysis feedback, String path) {
    if (feedback == null) {
      throw new IllegalArgumentException(path + " é nulo.");
    }
    if (blank(feedback.problem())) {
      throw new IllegalArgumentException(path + ".problem está vazio.");
    }
    if (blank(feedback.explanation())) {
      throw new IllegalArgumentException(path + ".explanation está vazio.");
    }
    if (blank(feedback.howToImprove())) {
      throw new IllegalArgumentException(path + ".howToImprove está vazio.");
    }
    if (blank(feedback.example())) {
      throw new IllegalArgumentException(path + ".example está vazio.");
    }
  }

  private boolean containsNormalized(String text, String excerpt) {
    return normalize(text).contains(normalize(excerpt));
  }

  private String normalize(String value) {
    String withoutAccents =
        Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    return withoutAccents
        .toLowerCase()
        .replaceAll("[^\\p{L}\\p{N}\\s]", " ")
        .replaceAll("\\s+", " ")
        .trim();
  }

  private boolean blank(String value) {
    return value == null || value.isBlank();
  }
}
