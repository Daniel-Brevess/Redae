package br.com.redae.shared.error;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiErrorResponse> handleApiException(
      ApiException exception, HttpServletRequest request) {
    return response(
        exception.getStatus(),
        exception.getCode(),
        exception.getMessage(),
        exception.getDetails(),
        request);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    List<ApiErrorDetail> details =
        exception.getBindingResult().getFieldErrors().stream()
            .map(this::toDetail)
            .toList();
    return response(
        HttpStatus.BAD_REQUEST,
        "VALIDATION_ERROR",
        "Os dados enviados são inválidos.",
        details,
        request);
  }

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<ApiErrorResponse> handleHandlerMethodValidation(
      HandlerMethodValidationException exception, HttpServletRequest request) {
    return response(
        HttpStatus.BAD_REQUEST,
        "VALIDATION_ERROR",
        "Os dados enviados são inválidos.",
        List.of(),
        request);
  }

  @ExceptionHandler({
    HttpMessageNotReadableException.class,
    MissingServletRequestParameterException.class
  })
  public ResponseEntity<ApiErrorResponse> handleMalformedRequest(
      Exception exception, HttpServletRequest request) {
    return response(
        HttpStatus.BAD_REQUEST,
        "INVALID_REQUEST",
        "Não foi possível interpretar os dados enviados.",
        List.of(),
        request);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiErrorResponse> handleAuthentication(
      AuthenticationException exception, HttpServletRequest request) {
    return response(
        HttpStatus.UNAUTHORIZED,
        "UNAUTHORIZED",
        "É necessário autenticar-se para acessar este recurso.",
        List.of(),
        request);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiErrorResponse> handleAccessDenied(
      AccessDeniedException exception, HttpServletRequest request) {
    return response(
        HttpStatus.FORBIDDEN,
        "FORBIDDEN",
        "Você não tem permissão para acessar este recurso.",
        List.of(),
        request);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiErrorResponse> handleMethodNotAllowed(
      HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
    return response(
        HttpStatus.METHOD_NOT_ALLOWED,
        "METHOD_NOT_ALLOWED",
        "O método HTTP não é permitido para este recurso.",
        List.of(),
        request);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleNotFound(
      NoResourceFoundException exception, HttpServletRequest request) {
    return response(
        HttpStatus.NOT_FOUND,
        "RESOURCE_NOT_FOUND",
        "O recurso solicitado não foi encontrado.",
        List.of(),
        request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleUnexpected(
      Exception exception, HttpServletRequest request) {
    log.error(
        "Unexpected error while processing {} {}",
        request.getMethod(),
        request.getRequestURI(),
        exception);
    return response(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "INTERNAL_ERROR",
        "Não foi possível concluir a solicitação.",
        List.of(),
        request);
  }

  private ApiErrorDetail toDetail(FieldError error) {
    String message =
        Objects.requireNonNullElse(error.getDefaultMessage(), "Valor inválido.");
    return new ApiErrorDetail(error.getField(), message);
  }

  private ResponseEntity<ApiErrorResponse> response(
      HttpStatus status,
      String code,
      String message,
      List<ApiErrorDetail> details,
      HttpServletRequest request) {
    String traceId = (String) request.getAttribute(TraceIdFilter.TRACE_ID_ATTRIBUTE);
    if (traceId == null || traceId.isBlank()) {
      traceId = java.util.UUID.randomUUID().toString();
    }
    return ResponseEntity.status(status)
        .body(new ApiErrorResponse(new ApiError(code, message, details), traceId));
  }
}
