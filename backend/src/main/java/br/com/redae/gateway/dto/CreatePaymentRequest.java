package br.com.redae.gateway.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreatePaymentRequest(
    @NotNull @Positive Integer creditAmount, @NotNull @Positive BigDecimal amount) {}
