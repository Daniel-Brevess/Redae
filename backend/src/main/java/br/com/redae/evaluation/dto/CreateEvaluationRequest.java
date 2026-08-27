package br.com.redae.evaluation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateEvaluationRequest(
    @NotBlank(message = "A origem é obrigatória.")
        @Pattern(regexp = "DIGITADA", message = "A origem deve ser DIGITADA.")
        String origin,
    @NotBlank(message = "O tema é obrigatório.") @Size(max = 500, message = "O tema deve ter no máximo 500 caracteres.") String theme,
    @NotBlank(message = "O texto da redação é obrigatório.")
        @Size(min = 80, max = 50000, message = "A redação deve ter entre 80 e 50000 caracteres.")
        String text) {}
