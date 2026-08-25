package br.com.redae.shared.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

class GlobalExceptionHandlerTest {
  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  void mapsApplicationExceptionToSafePublicEnvelope() {
    MockHttpServletRequest request = requestWithTraceId();

    var response =
        handler.handleApiException(
            new ResourceNotFoundException("A redação não foi encontrada."), request);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("RESOURCE_NOT_FOUND", response.getBody().error().code());
    assertEquals("A redação não foi encontrada.", response.getBody().error().message());
    assertEquals(
        request.getAttribute(TraceIdFilter.TRACE_ID_ATTRIBUTE), response.getBody().traceId());
  }

  @Test
  void mapsUnexpectedExceptionWithoutExposingInternalDetails() {
    MockHttpServletRequest request = requestWithTraceId();

    var response =
        handler.handleUnexpected(new IllegalStateException("database password"), request);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("INTERNAL_ERROR", response.getBody().error().code());
    assertEquals(
        "Não foi possível concluir a solicitação.", response.getBody().error().message());
    assertFalse(response.getBody().error().message().contains("database password"));
  }

  private HttpServletRequest requestWithTraceId() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setMethod("GET");
    request.setRequestURI("/api/v1/test");
    request.setAttribute(TraceIdFilter.TRACE_ID_ATTRIBUTE, UUID.randomUUID().toString());
    return request;
  }
}
