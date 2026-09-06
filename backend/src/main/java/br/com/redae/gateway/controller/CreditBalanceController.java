package br.com.redae.gateway.controller;

import br.com.redae.gateway.dto.CreditBalanceResponse;
import br.com.redae.gateway.service.PaymentService;
import br.com.redae.shared.http.ApiResponse;
import br.com.redae.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/credit-balance")
public class CreditBalanceController {
  private final PaymentService paymentService;

  public CreditBalanceController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<CreditBalanceResponse>> get(
      @AuthenticationPrincipal User user, HttpServletRequest httpRequest) {
    CreditBalanceResponse balance = paymentService.getCreditBalance(user);
    String traceId =
        (String)
            httpRequest.getAttribute(br.com.redae.shared.error.TraceIdFilter.TRACE_ID_ATTRIBUTE);
    return ResponseEntity.ok(ApiResponse.of(balance, traceId));
  }
}
