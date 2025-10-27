package br.com.logistica_alimentos.estoque.api.dto;

import br.com.logistica_alimentos.estoque.config.Enum.MotivoDescarte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DescarteProdutoRequest(
        @NotBlank String nome,
        @NotNull BigDecimal quantidade,
        @NotNull LocalDate validade,
        @NotBlank String origem,
        @NotNull MotivoDescarte motivo
) {
}
