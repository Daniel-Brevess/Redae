package br.com.redae.ai.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class GeminiClient implements AIClient {
  private final RestClient restClient;
  private final ObjectMapper objectMapper;
  private final String apiKey;
  private final String model;

  public GeminiClient(
      ObjectMapper objectMapper,
      @Value("${ai.gemini.api-key:}") String apiKey,
      @Value("${ai.gemini.model:gemini-3.6-flash}") String model) {
    this.objectMapper = objectMapper;
    this.apiKey = apiKey;
    this.model = model;
    this.restClient =
        RestClient.builder().baseUrl("https://generativelanguage.googleapis.com").build();
  }

  @Override
  public String generateStructured(String prompt, String responseSchema) {
    if (apiKey.isBlank()) {
      throw new IllegalStateException("A variável GOOGLE_API_KEY não está configurada.");
    }
    try {
      JsonNode schema = objectMapper.readTree(responseSchema);
      JsonNode response =
          restClient
              .post()
              .uri(
                  uriBuilder ->
                      uriBuilder
                          .path("/v1beta/models/{model}:generateContent")
                          .queryParam("key", apiKey)
                          .build(model))
              .contentType(MediaType.APPLICATION_JSON)
              .body(
                  Map.of(
                      "contents",
                      new Object[] {Map.of("parts", new Object[] {Map.of("text", prompt)})},
                      "generationConfig",
                      Map.of("responseMimeType", "application/json", "responseSchema", schema)))
              .retrieve()
              .body(JsonNode.class);
      JsonNode text =
          response.path("candidates").path(0).path("content").path("parts").path(0).path("text");
      if (!text.isTextual() || text.asText().isBlank()) {
        throw new IllegalStateException("O Gemini não retornou uma resposta estruturada.");
      }
      return text.asText();
    } catch (Exception exception) {
      throw new IllegalStateException("Não foi possível obter a avaliação da IA.", exception);
    }
  }

  @Override
  public String modelName() {
    return model;
  }
}
