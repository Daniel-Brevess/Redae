package br.com.redae.auth.controller;

import br.com.redae.auth.dto.LoginRequest;
import br.com.redae.auth.dto.RegisterRequest;
import br.com.redae.auth.dto.UserResponse;
import br.com.redae.auth.service.AuthService;
import br.com.redae.shared.http.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.redae.identity.entity.User;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
  private static final String REFRESH_COOKIE = "redae_refresh_token";
  private final AuthService authService;
  private final boolean secureCookie;
  private final String sameSite;

  public AuthController(
      AuthService authService,
      @Value("${security.cookie.secure:false}") boolean secureCookie,
      @Value("${security.cookie.same-site:Lax}") String sameSite) {
    this.authService = authService;
    this.secureCookie = secureCookie;
    this.sameSite = sameSite;
  }

  @PostMapping("/auth/register")
  public ResponseEntity<ApiResponse<UserResponse>> register(
      @Valid @RequestBody RegisterRequest request, HttpServletRequest httpRequest) {
    User user = authService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.of(UserResponse.from(user), traceId(httpRequest)));
  }

  @PostMapping("/auth/login")
  public ResponseEntity<ApiResponse<br.com.redae.auth.dto.AuthResponse>> login(
      @Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse response) {
    var result = authService.login(request);
    addRefreshCookie(response, result.refreshToken());
    return ResponseEntity.ok(ApiResponse.of(result.response(), traceId(httpRequest)));
  }

  @PostMapping("/auth/refresh")
  public ResponseEntity<ApiResponse<br.com.redae.auth.dto.AuthResponse>> refresh(
      HttpServletRequest request,
      HttpServletResponse response) {
    String refreshToken = extractCookie(request);
    var result = authService.refresh(refreshToken);
    addRefreshCookie(response, result.refreshToken());
    return ResponseEntity.ok(ApiResponse.of(result.response(), traceId(request)));
  }

  @PostMapping("/auth/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
    authService.logout(extractCookie(request));
    response.addHeader(HttpHeaders.SET_COOKIE, clearRefreshCookie().toString());
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/profile")
  public ResponseEntity<ApiResponse<UserResponse>> profile(
      @AuthenticationPrincipal User user, HttpServletRequest request) {
    return ResponseEntity.ok(ApiResponse.of(UserResponse.from(user), traceId(request)));
  }

  private void addRefreshCookie(HttpServletResponse response, String value) {
    response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie(value).toString());
  }

  private ResponseCookie refreshCookie(String value) {
    return ResponseCookie.from(REFRESH_COOKIE, value)
        .httpOnly(true)
        .secure(secureCookie)
        .sameSite(sameSite)
        .path("/api/v1/auth")
        .maxAge(Duration.ofDays(30))
        .build();
  }

  private ResponseCookie clearRefreshCookie() {
    return ResponseCookie.from(REFRESH_COOKIE, "")
        .httpOnly(true)
        .secure(secureCookie)
        .sameSite(sameSite)
        .path("/api/v1/auth")
        .maxAge(Duration.ZERO)
        .build();
  }

  private String extractCookie(HttpServletRequest request) {
    if (request.getCookies() == null) return null;
    for (var cookie : request.getCookies()) {
      if (REFRESH_COOKIE.equals(cookie.getName())) return cookie.getValue();
    }
    return null;
  }

  private String traceId(HttpServletRequest request) {
    return (String) request.getAttribute(br.com.redae.shared.error.TraceIdFilter.TRACE_ID_ATTRIBUTE);
  }
}
