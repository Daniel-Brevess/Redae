package br.com.redae.evaluation.dto;

import br.com.redae.evaluation.entity.Evaluation;
import br.com.redae.evaluation.entity.EvaluationType;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record EvaluationResponse(
    UUID id,
    String theme,
    String text,
    EvaluationType type,
    String origin,
    String status,
    Integer finalScore,
    String failureReason,
    List<CompetencyResponse> competencies,
    Instant createdAt) {
  public record CompetencyResponse(
      String code,
      int level,
      int points,
      String summary,
      List<String> highlights,
      List<FeedbackResponse> feedbackItems) {}

  public record FeedbackResponse(
      String excerpt,
      String problem,
      String explanation,
      String howToImprove,
      String example,
      String limitation) {}

  public static EvaluationResponse from(Evaluation evaluation) {
    return new EvaluationResponse(
        evaluation.getId(),
        evaluation.getTheme(),
        evaluation.getConfirmedText(),
        evaluation.getType(),
        evaluation.getOrigin().name(),
        evaluation.getStatus().name(),
        evaluation.getFinalScore(),
        evaluation.getFailureReason(),
        evaluation.getCompetencyScores().stream()
            .map(
                competency ->
                    new CompetencyResponse(
                        competency.getCode(),
                        competency.getLevel(),
                        competency.getPoints(),
                        competency.getSummary(),
                        highlights(evaluation, competency),
                        feedbackItems(evaluation, competency)))
            .toList(),
        evaluation.getCreatedAt());
  }

  public static EvaluationResponse summaryFrom(Evaluation evaluation) {
    EvaluationResponse response = from(evaluation);
    return new EvaluationResponse(
        response.id(),
        response.theme(),
        null,
        response.type(),
        response.origin(),
        response.status(),
        response.finalScore(),
        response.failureReason(),
        response.competencies().stream()
            .map(
                competency ->
                    new CompetencyResponse(
                        competency.code(),
                        competency.level(),
                        competency.points(),
                        competency.summary(),
                        competency.highlights(),
                        List.of()))
            .toList(),
        response.createdAt());
  }

  private static List<String> highlights(
      Evaluation evaluation, br.com.redae.evaluation.entity.CompetencyScore competency) {
    if (evaluation.getType() == EvaluationType.COMPLETA) return List.of();
    return competency.getFeedbackItems().stream()
        .map(feedback -> feedback.getProblem())
        .limit(2)
        .toList();
  }

  private static List<FeedbackResponse> feedbackItems(
      Evaluation evaluation, br.com.redae.evaluation.entity.CompetencyScore competency) {
    if (evaluation.getType() != EvaluationType.COMPLETA) return List.of();
    return competency.getFeedbackItems().stream()
        .map(
            feedback ->
                new FeedbackResponse(
                    feedback.getExcerpt(),
                    feedback.getProblem(),
                    feedback.getExplanation(),
                    feedback.getHowToImprove(),
                    feedback.getExample(),
                    feedback.getLimitation()))
        .toList();
  }
}
