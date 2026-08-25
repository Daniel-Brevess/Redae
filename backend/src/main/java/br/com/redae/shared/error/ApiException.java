package br.com.redae.shared.error;

import java.util.List;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
  private final HttpStatus status;
  private final String code;
  private final List<ApiErrorDetail> details;

  public ApiException(HttpStatus status, String code, String message) {
    this(status, code, message, List.of());
  }

  public ApiException(
      HttpStatus status, String code, String message, List<ApiErrorDetail> details) {
    super(message);
    this.status = status;
    this.code = code;
    this.details = details == null ? List.of() : List.copyOf(details);
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public List<ApiErrorDetail> getDetails() {
    return details;
  }
}
