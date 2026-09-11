package br.com.redae.admin.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

public record AdminCreditAdjustmentRequest(
    @NotNull UUID userId, @NotNull @Positive Integer credits) {}
