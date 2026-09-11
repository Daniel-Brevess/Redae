package br.com.redae.admin.controller;

import br.com.redae.admin.dto.AdminCreditAdjustmentRequest;
import br.com.redae.admin.dto.AdminCreditAdjustmentResponse;
import br.com.redae.admin.service.AdminService;
import br.com.redae.shared.http.ApiResponse;
import br.com.redae.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/credit-adjustments")
public class AdminCreditAdjustmentController {
  private final AdminService adminService;

  public AdminCreditAdjustmentController(AdminService adminService) {
    this.adminService = adminService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<AdminCreditAdjustmentResponse>> grantCredits(
      @Valid @RequestBody AdminCreditAdjustmentRequest request,
      @AuthenticationPrincipal User administrator,
      HttpServletRequest httpRequest) {
    var response = adminService.grantCredits(request.userId(), administrator, request.credits());
    String traceId =
        (String)
            httpRequest.getAttribute(br.com.redae.shared.error.TraceIdFilter.TRACE_ID_ATTRIBUTE);
    return ResponseEntity.ok(ApiResponse.of(response, traceId));
  }
}
