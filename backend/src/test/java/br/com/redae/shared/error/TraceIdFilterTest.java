package br.com.redae.shared.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class TraceIdFilterTest {
  private final TraceIdFilter filter = new TraceIdFilter();

  @Test
  void preservesValidTraceIdAndReturnsItInResponse() throws ServletException, IOException {
    String traceId = UUID.randomUUID().toString();
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(TraceIdFilter.TRACE_ID_HEADER, traceId);
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, new MockFilterChain());

    assertEquals(traceId, response.getHeader(TraceIdFilter.TRACE_ID_HEADER));
    assertEquals(traceId, request.getAttribute(TraceIdFilter.TRACE_ID_ATTRIBUTE));
  }

  @Test
  void generatesSafeTraceIdWhenHeaderIsInvalid() throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(TraceIdFilter.TRACE_ID_HEADER, "value-with-log-injection");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, new MockFilterChain());

    String traceId = response.getHeader(TraceIdFilter.TRACE_ID_HEADER);
    assertNotEquals("value-with-log-injection", traceId);
    assertTrue(() -> UUID.fromString(traceId) != null);
  }
}


