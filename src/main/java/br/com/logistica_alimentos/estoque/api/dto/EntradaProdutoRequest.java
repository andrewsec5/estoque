package br.com.logistica_alimentos.estoque.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EntradaProdutoRequest(
        @NotBlank String nome,
        @NotNull BigDecimal quantidade,
        @NotNull LocalDate validade,
        @NotBlank String origem
) {
}
