package br.com.redae.auth.dto;

public record AuthResponse(String accessToken, long expiresIn, UserResponse user) {}
