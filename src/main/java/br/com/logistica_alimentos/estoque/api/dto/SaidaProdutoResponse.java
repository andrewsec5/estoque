package br.com.logistica_alimentos.estoque.api.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record SaidaProdutoResponse(
        String nome,
        BigDecimal quantidade,
        LocalDate validade,
        String origem,
        LocalDateTime dataSaida
) {
}
