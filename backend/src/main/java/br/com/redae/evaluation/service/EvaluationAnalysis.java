package br.com.redae.evaluation.service;

import java.util.List;

public record EvaluationAnalysis(List<CompetencyAnalysis> competencies) {
  public record CompetencyAnalysis(
      String code, int level, String summary, List<FeedbackAnalysis> feedbackItems) {}

  public record FeedbackAnalysis(
      String excerpt,
      String problem,
      String explanation,
      String howToImprove,
      String example,
      String limitation) {}
}
