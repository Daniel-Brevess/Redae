package br.com.redae.ai.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class OpenAIClient implements AIClient {
  private static final String RESPONSE_SCHEMA =
      """
          {
            "type": "object",
            "additionalProperties": false,
            "properties": {
              "competencies": {
                "type": "array",
                "items": {
                  "type": "object",
                  "additionalProperties": false,
                  "properties": {
                    "code": {
                      "type": "string",
                      "enum": ["C1", "C2", "C3", "C4", "C5"]
                    },
                    "level": {
                      "type": "integer",
                      "minimum": 0,
                      "maximum": 5
                    },
                    "summary": {
                      "type": "string"
                    },
                    "feedbackItems": {
                      "type": "array",
                      "items": {
                        "type": "object",
                        "additionalProperties": false,
                        "properties": {
                          "excerpt": {
                            "type": "string"
                          },
                          "problem": {
                            "type": "string"
                          },
                          "explanation": {
                            "type": "string"
                          },
                          "howToImprove": {
                            "type": "string"
                          },
                          "example": {
                            "type": "string"
                          }
                        },
                        "required": [
                          "excerpt",
                          "problem",
                          "explanation",
                          "howToImprove",
                          "example"
                        ]
                      }
                    }
                  },
                  "required": ["code", "level", "summary", "feedbackItems"]
                }
              }
            },
            "required": ["competencies"]
          }
          """;

  private final RestClient restClient;
  private final ObjectMapper objectMapper;
  private final String apiKey;
  private final String model;

  public OpenAIClient(ObjectMapper objectMapper, String apiKey, String model) {
    this.objectMapper = objectMapper;
    this.apiKey = apiKey;
    this.model = model;
    this.restClient = RestClient.builder().baseUrl("https://api.openai.com").build();
  }

  @Override
  public String generateStructured(String prompt, String responseSchema) {
    if (apiKey.isBlank()) {
      throw new IllegalStateException("A variável OPENAI_API_KEY não está configurada.");
    }
    try {
      JsonNode response =
          restClient
              .post()
              .uri("/v1/chat/completions")
              .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
              .contentType(MediaType.APPLICATION_JSON)
              .body(
                  Map.of(
                      "model",
                      model,
                      "messages",
                      new Object[] {Map.of("role", "user", "content", prompt)},
                      "response_format",
                      Map.of(
                          "type",
                          "json_schema",
                          "json_schema",
                          Map.of(
                              "name",
                              "evaluation_analysis",
                              "strict",
                              true,
                              "schema",
                              objectMapper.readTree(RESPONSE_SCHEMA)))))
              .retrieve()
              .body(JsonNode.class);
      JsonNode content = response.path("choices").path(0).path("message").path("content");
      if (!content.isTextual() || content.asText().isBlank()) {
        throw new IllegalStateException("A OpenAI não retornou uma resposta estruturada.");
      }
      objectMapper.readTree(content.asText());
      return content.asText();
    } catch (Exception exception) {
      throw new IllegalStateException("Não foi possível obter a avaliação da IA.", exception);
    }
  }

  @Override
  public String modelName() {
    return model;
  }
}
