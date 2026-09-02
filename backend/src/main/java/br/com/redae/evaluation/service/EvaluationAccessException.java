package br.com.redae.evaluation.service;

import br.com.redae.shared.error.ApiException;
import org.springframework.http.HttpStatus;

public class EvaluationAccessException extends ApiException {
  public EvaluationAccessException() {
    super(
        HttpStatus.FORBIDDEN,
        "CREDIT_REQUIRED",
        "Você já realizou o diagnóstico gratuito. Adquira créditos para fazer uma avaliação completa.");
  }
}
