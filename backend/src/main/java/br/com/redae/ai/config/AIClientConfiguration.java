package br.com.redae.ai.config;

import br.com.redae.ai.client.GeminiClient;
import br.com.redae.ai.client.OpenAIClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIClientConfiguration {
  @Bean
  @ConditionalOnProperty(name = "ai.provider", havingValue = "gemini")
  GeminiClient geminiClient(
      ObjectMapper objectMapper,
      @Value("${ai.gemini.api-key:}") String apiKey,
      @Value("${ai.gemini.model:gemini-3.6-flash}") String model) {
    return new GeminiClient(objectMapper, apiKey, model);
  }

  @Bean
  @ConditionalOnProperty(name = "ai.provider", havingValue = "openai", matchIfMissing = true)
  OpenAIClient openAIClient(
      ObjectMapper objectMapper,
      @Value("${ai.openai.api-key:}") String apiKey,
      @Value("${ai.openai.model:gpt-4o-mini}") String model) {
    return new OpenAIClient(objectMapper, apiKey, model);
  }
}
