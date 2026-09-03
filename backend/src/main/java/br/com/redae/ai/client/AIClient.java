package br.com.redae.ai.client;

public interface AIClient {
  String generateStructured(String prompt, String responseSchema);

  String modelName();
}
