package br.com.redae.shared.http;

import java.util.Map;

public record ApiResponse<T>(T data, Map<String, Object> meta, String traceId) {
  public static <T> ApiResponse<T> of(T data, String traceId) {
    return new ApiResponse<>(data, Map.of(), traceId);
  }
}
