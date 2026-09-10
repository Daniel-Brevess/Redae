package br.com.redae.admin.controller;

import br.com.redae.admin.dto.AdminUserCountResponse;
import br.com.redae.admin.service.AdminService;
import br.com.redae.shared.http.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {
  private final AdminService adminService;

  public AdminUserController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/count")
  public ResponseEntity<ApiResponse<AdminUserCountResponse>> count(HttpServletRequest httpRequest) {
    var response = new AdminUserCountResponse(adminService.countUsers());
    return ResponseEntity.ok(ApiResponse.of(response, traceId(httpRequest)));
  }

  private String traceId(HttpServletRequest request) {
    return (String)
        request.getAttribute(br.com.redae.shared.error.TraceIdFilter.TRACE_ID_ATTRIBUTE);
  }
}
