package br.com.redae.gateway.controller;

import br.com.redae.gateway.dto.CreatePaymentRequest;
import br.com.redae.gateway.dto.PaymentResponse;
import br.com.redae.gateway.dto.PaymentTransactionResponse;
import br.com.redae.gateway.service.PaymentService;
import br.com.redae.shared.http.ApiResponse;
import br.com.redae.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/purchases")
public class PaymentController {
  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<PaymentResponse>> create(
      @AuthenticationPrincipal User user,
      @Valid @RequestBody CreatePaymentRequest request,
      HttpServletRequest httpRequest) {
    PaymentResponse payment = paymentService.create(user, request);
    String traceId =
        (String)
            httpRequest.getAttribute(br.com.redae.shared.error.TraceIdFilter.TRACE_ID_ATTRIBUTE);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(payment, traceId));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<PaymentTransactionResponse>>> list(
      @AuthenticationPrincipal User user, HttpServletRequest httpRequest) {
    List<PaymentTransactionResponse> transactions = paymentService.listTransactions(user);
    String traceId = traceId(httpRequest);
    return ResponseEntity.ok(ApiResponse.of(transactions, traceId));
  }

  private String traceId(HttpServletRequest httpRequest) {
    return (String)
        httpRequest.getAttribute(br.com.redae.shared.error.TraceIdFilter.TRACE_ID_ATTRIBUTE);
  }
}
