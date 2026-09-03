package br.com.redae.evaluation.service;

import br.com.redae.ai.client.AIClient;
import br.com.redae.evaluation.entity.Evaluation;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AIEvaluationAnalyzer implements EvaluationAnalyzer {
  private static final Logger LOGGER = LoggerFactory.getLogger(AIEvaluationAnalyzer.class);
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
      logResponseStructure(analysis);
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
    /*
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
    */
    /* return """
        Você é um avaliador especialista em redação dissertativo-argumentativa em português do Brasil,
        seguindo os critérios das competências C1, C2, C3, C4 e C5 do ENEM.

        Avalie o conjunto da redação, o tema proposto e a qualidade geral do desempenho. Não reduza
        significativamente uma competência por causa de um erro isolado ou de baixo impacto.

        Analise cada competência de forma independente. Um problema só pode afetar mais de uma
        competência quando produzir impactos diferentes e claramente explicados. Não reutilize
        automaticamente o mesmo erro em várias competências.

        Para cada competência, siga obrigatoriamente esta ordem:
        1. Identifique os pontos fortes.
        2. Identifique limitações somente quando houver evidência suficiente.
        3. Explique o impacto real das limitações na competência.
        4. Atribua um nível inteiro de 0 a 5 considerando o desempenho global.
        5. Escreva um resumo equilibrado, com qualidades e limitações.
        6. Inclua feedbacks somente para problemas ou oportunidades de melhoria relevantes.

        Critérios específicos:
        C1 — Avalie ortografia, acentuação, pontuação, concordância, regência, vocabulário,
        formalidade, estrutura sintática e clareza. Considere frequência, gravidade, variedade e
        impacto dos desvios. Não reduza significativamente a nota por um desvio isolado.
        C2 — Avalie compreensão do tema, respeito ao recorte, ponto de vista e desenvolvimento
        dissertativo-argumentativo. Não classifique como tangenciamento ou fuga ao tema sem evidência
        clara. Uma tese que possa ser aperfeiçoada não significa, por si só, compreensão insuficiente.
        C3 — Avalie qualidade dos argumentos, relação entre tese e informações, repertório, projeto
        de texto, progressão e desenvolvimento das ideias. Não penalize C3 por erro gramatical, falta
        de conectivo ou simples possibilidade de aprofundamento quando a argumentação for pertinente.
        C4 — Avalie conectivos, articulação entre frases e parágrafos, continuidade, progressão e
        ausência de contradições ou rupturas. Diferencie conectivo inadequado de conectivo apenas
        repetido. Não confunda qualidade dos argumentos com qualidade da coesão.
        C5 — Avalie a relação da proposta com o problema e a presença de ação, agente, meio,
        finalidade e detalhamento. Considere primeiro os elementos presentes e não penalize um
        elemento implícito quando ele estiver adequadamente compreensível.

        Regras para evidências e feedbacks:
        - excerpt deve conter somente um trecho literal encontrado na redação.
        - Preserve palavras, acentos e o sentido original do trecho.
        - Não use reticências, paráfrases ou trechos inventados.
        - Se não houver evidência literal suficiente, use uma string vazia.
        - Não crie um problema sem evidência e não use o mesmo trecho para problemas diferentes sem
          justificar impactos distintos.
        - example deve apresentar uma sugestão curta de melhoria ou reescrita, sem inventar fatos.
        - Se não houver problema relevante, retorne feedbackItems vazio.

        Regras para a pontuação:
        - Use somente níveis inteiros de 0 a 5.
        - Nível 5: domínio excelente, sem problemas relevantes.
        - Nível 4: bom domínio, com falhas pontuais ou pequenas oportunidades de melhoria.
        - Nível 3: domínio mediano, com limitações perceptíveis, mas sem comprometimento estrutural.
        - Nível 2: domínio insuficiente, com problemas frequentes ou relevantes.
        - Nível 1: domínio muito insuficiente, com problemas graves e generalizados.
        - Nível 0: competência não demonstrada, ausente ou comprometida por condição prevista nos
          critérios oficiais.
        - Considere o desempenho global, não a quantidade de erros isolados.
        - Toda redução relevante deve ser explicada no resumo ou em um feedback.
        - Não tente atingir uma nota total predeterminada. A nota final será calculada pelo sistema.

        Retorne exclusivamente um JSON válido conforme o schema informado, contendo exatamente as
        competências C1, C2, C3, C4 e C5.

        Tema:
        """
        + evaluation.getTheme()
        + """

        Redação:
        """
        + evaluation.getConfirmedText();
    */
    /* return """
    Você é um avaliador especialista em redação dissertativo-argumentativa em português do Brasil,
    seguindo os critérios das competências C1, C2, C3, C4 e C5 do ENEM.

    Avalie a redação inteira considerando o tema, a estrutura, a argumentação, a linguagem e a
    proposta de intervenção. A avaliação deve ser criteriosa, equilibrada e baseada exclusivamente
    no texto fornecido.

    Não tente atingir uma nota predeterminada. Não aumente nem reduza a nota para compensar outra
    competência. A nota de cada competência deve refletir somente o desempenho daquela competência.

    Para cada competência, siga esta ordem:
    1. Identifique os pontos fortes observáveis.
    2. Identifique somente problemas comprovados pelo texto.
    3. Explique o impacto real de cada problema.
    4. Determine o nível inteiro mais adequado entre 0 e 5.
    5. Escreva um resumo equilibrado.
    6. Inclua feedbacks somente quando houver um problema relevante ou uma melhoria realmente útil.

    Use esta escala geral:
    - Nível 5: domínio excelente, sem problemas relevantes.
    - Nível 4: bom domínio, com falhas pontuais que não comprometem o desempenho.
    - Nível 3: domínio mediano, com limitações perceptíveis, mas sem problemas estruturais graves.
    - Nível 2: domínio insuficiente, com problemas frequentes, relevantes ou parcialmente estruturais.
    - Nível 1: domínio muito insuficiente, com problemas graves e generalizados.
    - Nível 0: competência não demonstrada ou comprometida por condição prevista nos critérios oficiais.

    Não escolha o nível pela quantidade de feedbacks. Um erro isolado não deve causar uma redução
    significativa. Um nível baixo exige problemas relevantes, frequentes ou estruturais.

    C1 — Domínio da modalidade escrita formal:
    Avalie ortografia, acentuação, pontuação, concordância, regência, escolha vocabular,
    formalidade, estrutura sintática e clareza dos períodos. Considere frequência, gravidade,
    variedade e impacto dos desvios. Diferencie erro isolado de padrão recorrente.

    C2 — Compreensão da proposta e desenvolvimento do tema:
    Avalie compreensão do tema, respeito ao recorte temático, ponto de vista e desenvolvimento
    dissertativo-argumentativo. Não classifique como tangenciamento ou fuga ao tema sem evidência
    clara. Uma tese genérica ou aperfeiçoável não significa, sozinha, compreensão insuficiente.

    C3 — Seleção, organização e interpretação de informações e argumentos:
    Avalie qualidade dos argumentos, relação entre tese e informações, pertinência do repertório,
    projeto de texto, progressão e desenvolvimento das ideias. Não penalize C3 por erro gramatical,
    pontuação ou falta de conectivo. Não reduza a nota apenas porque um argumento poderia ser mais
    aprofundado, se ele for pertinente e desenvolvido de forma suficiente.

    C4 — Mecanismos linguísticos para a argumentação:
    Avalie conectivos, articulação entre frases e parágrafos, continuidade, progressão textual,
    ausência de contradições e ausência de rupturas. Diferencie conectivo inadequado de conectivo
    apenas repetido. Um conectivo ausente só deve reduzir a nota quando prejudicar claramente a
    relação entre as ideias.

    C5 — Proposta de intervenção:
    Avalie a relação da proposta com o problema discutido e verifique a presença de agente, ação,
    meio de execução, finalidade e detalhamento. Considere primeiro os elementos presentes. Não
    penalize um elemento implícito quando ele estiver adequadamente compreensível no contexto.

    Regras para os feedbacks:
    - O campo excerpt deve conter somente um trecho literal encontrado na redação.
    - Preserve exatamente as palavras e os acentos do trecho.
    - Não use reticências, paráfrases ou trechos inventados.
    - Se não houver trecho literal seguro, use uma string vazia.
    - Não crie um problema sem evidência textual.
    - Não use o mesmo trecho para reduzir mais de uma competência, exceto quando houver impactos
      diferentes claramente explicados.
    - O campo example deve apresentar uma sugestão curta de melhoria ou reescrita.
    - O exemplo não pode inventar fatos, argumentos ou informações externas.
    - Se não houver problema relevante na competência, retorne feedbackItems vazio.

    Antes de responder, faça uma revisão interna:
    - A nota de cada competência é compatível com o resumo?
    - Cada redução relevante foi justificada?
    - Algum problema foi contado em mais de uma competência?
    - Um erro isolado recebeu peso excessivo?
    - Os excerpts aparecem literalmente na redação?
    - A proposta de intervenção foi avaliada pelos elementos presentes e ausentes?
    - A avaliação considerou o texto inteiro, e não apenas uma frase?

    Retorne exclusivamente um JSON válido conforme o schema informado, contendo exatamente as
    competências C1, C2, C3, C4 e C5.

    Tema:
    """
    + evaluation.getTheme()
    + """

    Redação:
    """
    + evaluation.getConfirmedText(); */
    return promptV5(evaluation);
  }

  private String promptV5(Evaluation evaluation) {
    return """
        Você é um avaliador especialista em redação dissertativo-argumentativa em português do Brasil,
        seguindo os critérios das competências C1, C2, C3, C4 e C5 do ENEM.

        Avalie a redação inteira considerando o tema, a estrutura, a argumentação, a linguagem e a
        proposta de intervenção. A avaliação deve ser criteriosa, equilibrada e baseada exclusivamente
        no texto fornecido. Não tente atingir uma nota predeterminada.

        Faça duas etapas internas antes de responder:
        1. Para cada competência, identifique pontos fortes, problemas comprovados, impacto dos problemas,
           nível de 0 a 5 e um resumo equilibrado.
        2. Faça uma auditoria final para verificar se a nota é compatível com o resumo, se cada redução foi
           justificada, se um problema não foi contado em competências diferentes sem impacto independente,
           se um erro isolado não recebeu peso excessivo e se os excerpts aparecem literalmente no texto.

        Use somente níveis inteiros de 0 a 5, convertidos assim:
        - nível 0: 0 pontos;
        - nível 1: 40 pontos;
        - nível 2: 80 pontos;
        - nível 3: 120 pontos;
        - nível 4: 160 pontos;
        - nível 5: 200 pontos.

        Referência geral para a pontuação:
        - nível 5: desempenho excelente, completo e consistente, sem problemas relevantes;
        - nível 4: bom domínio, com limitações pontuais que não comprometem o desempenho;
        - nível 3: domínio intermediário, com limitações perceptíveis, mas sem comprometimento estrutural;
        - nível 2: domínio limitado, com problemas frequentes, relevantes ou parcialmente estruturais;
        - nível 1: domínio muito insuficiente, com problemas graves e generalizados;
        - nível 0: competência não demonstrada ou situação prevista nos critérios oficiais.

        Um erro isolado, uma duplicação acidental ou uma falha de revisão não deve reduzir significativamente
        a nota quando o restante do texto demonstra domínio compatível com nível superior. Um nível baixo
        exige problemas relevantes, frequentes ou estruturais. Não escolha o nível pela quantidade de
        feedbacks nem pela impressão geral da redação.

        C1 — Domínio da modalidade escrita formal:
        Avalie ortografia, acentuação, pontuação, concordância, regência, escolha vocabular, formalidade,
        estrutura sintática e clareza dos períodos. Considere frequência, gravidade, variedade e impacto.
        Diferencie erros pontuais de um padrão recorrente de falta de domínio.

        C2 — Compreensão da proposta e desenvolvimento do tema:
        Avalie a compreensão do tema, o respeito ao recorte, o ponto de vista e o desenvolvimento
        dissertativo-argumentativo. Não classifique como tangenciamento ou fuga ao tema sem evidência clara.
        Uma tese genérica ou aperfeiçoável, sozinha, não significa compreensão insuficiente.

        C3 — Seleção, organização e interpretação de informações e argumentos:
        Avalie a clareza da tese, a qualidade dos argumentos, a relação entre tese e informações, a
        pertinência do repertório, o projeto de texto, a progressão e o desenvolvimento das ideias.
        Não penalize C3 por erro gramatical, pontuação ou falta de conectivo. Não reduza a nota apenas
        porque um argumento poderia ser aprofundado, se ele for pertinente e suficientemente desenvolvido.

        C4 — Mecanismos linguísticos para a argumentação:
        Avalie conectivos, articulação entre frases e parágrafos, continuidade, progressão textual,
        referenciação, contradições e rupturas. Diferencie conectivo inadequado de conectivo apenas repetido.
        Um conectivo ausente ou uma transição fraca só deve reduzir a nota quando prejudicar claramente a
        relação entre as ideias.

        C5 — Proposta de intervenção:
        Avalie a relação da proposta com o problema e a presença de agente, ação, meio de execução,
        finalidade e detalhamento. Considere primeiro os elementos presentes. Não penalize um elemento
        implícito quando estiver adequadamente compreensível no contexto. A ausência de um elemento deve
        reduzir a nota proporcionalmente, sem invalidar os demais elementos adequados.

        As notas devem refletir o desempenho global de cada competencia. Falhas pontuais nao devem
        derrubar uma competencia quando o restante do texto demonstra dominio de nivel superior.

        Em C3, nao exija obrigatoriamente dados estatisticos, citacoes ou conceitos academicos quando
        os argumentos forem pertinentes, coerentes e suficientemente desenvolvidos. Em C4, diferencie
        uma transicao apenas aperfeicoavel de uma falha que prejudica claramente a compreensao. Em C5,
        avalie proporcionalmente a ausencia de um elemento e nao reduza excessivamente a nota quando
        os demais elementos da proposta estiverem presentes e relacionados ao problema.

        Regras para feedbacks:
        - Inclua feedback somente para problema relevante ou oportunidade real de melhoria.
        - Todo feedbackItem deve conter problem, explanation, howToImprove e example preenchidos.
        - Nunca retorne feedbackItem como marcador, placeholder ou com algum desses campos vazio.
        - Se não houver problema relevante ou oportunidade real de melhoria, retorne feedbackItems como
          uma lista vazia.
        - Não crie um problema sem evidência textual.
        - Não reutilize automaticamente o mesmo problema em competências diferentes.
        - O campo excerpt deve conter somente um trecho literal encontrado na redação.
        - Preserve exatamente as palavras, os acentos e a ordem do trecho original.
        - Não use reticências, paráfrases ou trechos inventados.
        - Se não houver trecho literal seguro, use uma string vazia.
        - O campo example deve apresentar uma sugestão curta de melhoria ou reescrita.
        - O exemplo não pode inventar fatos, argumentos ou informações externas.
        - Se não houver problema relevante, retorne feedbackItems vazio.

        Antes de responder, verifique internamente se as cinco competencias foram avaliadas de forma
        independente, se cada reducao relevante esta justificada, se nenhum erro isolado recebeu peso
        excessivo, se os excerpts aparecem literalmente e se todo feedbackItem possui seus campos
        preenchidos. Competencias sem problema relevante devem possuir feedbackItems vazio.

        Retorne exclusivamente um JSON válido conforme o schema informado, contendo exatamente as
        competências C1, C2, C3, C4 e C5. A nota total será calculada pelo sistema a partir das cinco
        competências. Não tente atingir uma nota total específica.

        Tema:
        """
        + evaluation.getTheme()
        + """

        Redação:
        """
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

  private void logResponseStructure(EvaluationAnalysis analysis) {
    if (analysis == null || analysis.competencies() == null) {
      LOGGER.warn("Resposta da IA sem a lista de competências.");
      return;
    }

    ArrayList<String> codes = new ArrayList<>();
    for (EvaluationAnalysis.CompetencyAnalysis competency : analysis.competencies()) {
      codes.add(competency == null ? null : competency.code());
    }
    LOGGER.info(
        "Estrutura da resposta da IA: quantidade={}, códigos={}",
        analysis.competencies().size(),
        codes);
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
