package br.com.redae.shared.error;

public record ApiErrorResponse(ApiError error, String traceId) {}
