package br.com.redae.evaluation.controller;

import br.com.redae.evaluation.dto.CreateEvaluationRequest;
import br.com.redae.evaluation.dto.EvaluationResponse;
import br.com.redae.evaluation.service.EvaluationService;
import br.com.redae.identity.entity.User;
import br.com.redae.shared.http.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/evaluations")
public class EvaluationController {
  private final EvaluationService evaluationService;

  public EvaluationController(EvaluationService evaluationService) {
    this.evaluationService = evaluationService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<EvaluationResponse>> create(
      @AuthenticationPrincipal User user,
      @Valid @RequestBody CreateEvaluationRequest request,
      HttpServletRequest httpRequest) {
    var evaluation = evaluationService.createTypedEvaluation(user, request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.of(EvaluationResponse.from(evaluation), traceId(httpRequest)));
  }

  @GetMapping("/{evaluationId}")
  public ResponseEntity<ApiResponse<EvaluationResponse>> find(
      @AuthenticationPrincipal User user,
      @PathVariable UUID evaluationId,
      HttpServletRequest httpRequest) {
    var evaluation = evaluationService.findOwnedEvaluation(user, evaluationId);
    return ResponseEntity.ok(
        ApiResponse.of(EvaluationResponse.from(evaluation), traceId(httpRequest)));
  }

  private String traceId(HttpServletRequest request) {
    return (String)
        request.getAttribute(br.com.redae.shared.error.TraceIdFilter.TRACE_ID_ATTRIBUTE);
  }
}
