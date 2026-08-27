package br.com.redae.evaluation.service;

import br.com.redae.evaluation.entity.Evaluation;

public interface EvaluationAnalyzer {
  EvaluationAnalysis analyze(Evaluation evaluation);

  String modelName();
}
