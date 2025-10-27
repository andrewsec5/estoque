package br.com.logistica_alimentos.estoque.api.dto;

import br.com.logistica_alimentos.estoque.config.Enum.MotivoDescarte;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DescarteProdutoResponse(
        String nome,
        BigDecimal quantidade,
        LocalDate validade,
        MotivoDescarte motivoDescarte,
        String origem,
        LocalDateTime dataDescarte
) {
}
