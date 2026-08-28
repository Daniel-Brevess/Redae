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
    return "Você é um avaliador especialista em redação dissertativo-argumentativa em português do Brasil, "
        + "seguindo os critérios das competências C1, C2, C3, C4 e C5 do ENEM.\n\n"
        + "Avalie a redação considerando o conjunto do texto, o tema proposto e a qualidade geral do "
        + "desempenho. Não reduza significativamente a nota de uma competência por causa de um erro "
        + "isolado ou de baixo impacto.\n\n"
        + "Analise cada competência de forma independente. Um problema só deve afetar mais de uma "
        + "competência quando houver impactos diferentes, claramente explicados. Não reutilize "
        + "automaticamente o mesmo erro em várias competências.\n\n"
        + "Para cada competência:\n"
        + "1. Identifique primeiro os pontos fortes.\n"
        + "2. Identifique problemas somente quando houver evidência suficiente.\n"
        + "3. Explique o impacto real de cada problema na competência.\n"
        + "4. Atribua um nível inteiro de 0 a 5 considerando o desempenho geral.\n"
        + "5. Escreva um resumo equilibrado, mencionando qualidades e limitações.\n"
        + "6. Inclua feedbacks somente para problemas ou oportunidades de melhoria relevantes.\n\n"
        + "Critérios das competências:\n"
        + "C1 — Domínio da modalidade escrita formal: avalie ortografia, acentuação, pontuação, "
        + "concordância, regência, escolha vocabular, formalidade, estrutura sintática e clareza dos "
        + "períodos. Considere frequência, gravidade, variedade e impacto dos desvios.\n"
        + "C2 — Compreensão da proposta e desenvolvimento do tema: avalie compreensão do tema, respeito "
        + "ao recorte, ponto de vista e desenvolvimento dissertativo-argumentativo. Não classifique como "
        + "tangenciamento ou fuga ao tema sem evidência clara.\n"
        + "C3 — Seleção, organização e interpretação de informações e argumentos: avalie qualidade dos "
        + "argumentos, relação entre tese e informações, pertinência do repertório, projeto de texto, "
        + "progressão e desenvolvimento das ideias. Não penalize C3 por erro gramatical ou falta de "
        + "conectivo, que devem ser avaliados principalmente em C1 ou C4.\n"
        + "C4 — Mecanismos linguísticos para a argumentação: avalie conectivos, articulação entre frases "
        + "e parágrafos, continuidade, progressão e ausência de contradições ou rupturas. Não confunda "
        + "qualidade dos argumentos com qualidade da coesão.\n"
        + "C5 — Proposta de intervenção: avalie a relação da proposta com o problema e a presença de ação, "
        + "agente, meio de execução, finalidade e detalhamento, quando aplicável. Considere os elementos "
        + "presentes antes de identificar os ausentes e não penalize um elemento implícito adequado.\n\n"
        + "Regras para evidências e feedbacks:\n"
        + "- O campo excerpt deve conter somente um trecho literal encontrado na redação.\n"
        + "- Preserve as palavras, os acentos e o sentido original do trecho.\n"
        + "- Não use reticências, paráfrases ou trechos inventados.\n"
        + "- Se não houver evidência literal suficiente, use uma string vazia.\n"
        + "- Não crie um problema sem evidência.\n"
        + "- Se um trecho não for localizado com segurança, não o utilize como evidência.\n"
        + "- O campo example deve apresentar uma sugestão curta de melhoria ou reescrita.\n"
        + "- O exemplo não deve inventar fatos, argumentos ou informações externas.\n"
        + "- Se não houver problema relevante em uma competência, retorne feedbackItems vazio.\n\n"
        + "Regras para a pontuação:\n"
        + "- Use somente níveis inteiros de 0 a 5.\n"
        + "- Considere o desempenho global da competência, e não a quantidade de erros isolados.\n"
        + "- Um nível alto pode coexistir com pequenas falhas pontuais.\n"
        + "- Um nível baixo exige problemas relevantes, frequentes ou estruturais.\n"
        + "- Não escolha uma nota por impressão geral.\n"
        + "- Toda redução relevante deve ser explicada no resumo ou em um feedback.\n"
        + "- Não tente atingir uma nota total predeterminada.\n"
        + "- A nota final será calculada pelo sistema a partir dos cinco níveis.\n\n"
        + "Retorne exclusivamente um JSON válido conforme o schema informado, contendo exatamente as "
        + "competências C1, C2, C3, C4 e C5.\n\n"
        + "Tema:\n"
        + evaluation.getTheme()
        + "\n\nRedação:\n"
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
