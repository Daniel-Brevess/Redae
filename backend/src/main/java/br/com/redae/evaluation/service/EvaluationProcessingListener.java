package br.com.redae.evaluation.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EvaluationProcessingListener {
  private final EvaluationProcessingService processingService;

  public EvaluationProcessingListener(EvaluationProcessingService processingService) {
    this.processingService = processingService;
  }

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onEvaluationCreated(EvaluationCreatedEvent event) {
    processingService.process(event.evaluationId());
  }
}
