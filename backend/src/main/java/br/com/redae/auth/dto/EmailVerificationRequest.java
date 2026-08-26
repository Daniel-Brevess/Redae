package br.com.redae.auth.dto;

import jakarta.validation.constraints.Pattern;

public record EmailVerificationRequest(
    @Pattern(regexp = "\\d{6}", message = "O código deve ter 6 dígitos.") String code) {}
