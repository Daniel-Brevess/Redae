package br.com.redae.ai.client;

public interface AIClient {
  String generateStructured(String prompt, String responseSchema);

  String transcribeImage(byte[] image, String contentType, String prompt);

  String modelName();
}
