package br.com.redae.ai.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class OpenAIClientTest {
  @Test
  void exposesConfiguredModelName() {
    OpenAIClient client = new OpenAIClient(new ObjectMapper(), "key", "gpt-4o-mini");

    assertEquals("gpt-4o-mini", client.modelName());
  }

  @Test
  void rejectsRequestWhenApiKeyIsMissing() {
    OpenAIClient client = new OpenAIClient(new ObjectMapper(), "", "gpt-4o-mini");

    IllegalStateException exception =
        assertThrows(IllegalStateException.class, () -> client.generateStructured("prompt", "{}"));

    assertEquals("A variável OPENAI_API_KEY não está configurada.", exception.getMessage());
  }
}
