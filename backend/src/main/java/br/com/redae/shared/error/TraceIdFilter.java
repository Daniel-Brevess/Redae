package br.com.redae.shared.error;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TraceIdFilter extends OncePerRequestFilter {
  public static final String TRACE_ID_HEADER = "X-Trace-Id";
  public static final String TRACE_ID_ATTRIBUTE = TraceIdFilter.class.getName() + ".traceId";
  private static final String MDC_KEY = "traceId";

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String traceId = resolveTraceId(request.getHeader(TRACE_ID_HEADER));
    request.setAttribute(TRACE_ID_ATTRIBUTE, traceId);
    response.setHeader(TRACE_ID_HEADER, traceId);
    MDC.put(MDC_KEY, traceId);

    try {
      filterChain.doFilter(request, response);
    } finally {
      MDC.remove(MDC_KEY);
    }
  }

  private String resolveTraceId(String candidate) {
    if (candidate == null || candidate.isBlank()) {
      return UUID.randomUUID().toString();
    }

    try {
      return UUID.fromString(candidate).toString();
    } catch (IllegalArgumentException exception) {
      return UUID.randomUUID().toString();
    }
  }
}


