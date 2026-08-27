package br.com.redae.evaluation.dto;

import br.com.redae.evaluation.entity.Evaluation;
import java.time.Instant;
import java.util.UUID;

public record EvaluationResponse(
    UUID id, String theme, String origin, String status, Instant createdAt) {
  public static EvaluationResponse from(Evaluation evaluation) {
    return new EvaluationResponse(
        evaluation.getId(),
        evaluation.getTheme(),
        evaluation.getOrigin().name(),
        evaluation.getStatus().name(),
        evaluation.getCreatedAt());
  }
}
