package br.com.redae.evaluation.service;

public interface AIClient {
  String generateStructured(String prompt, String responseSchema);

  String modelName();
}
