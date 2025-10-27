package br.com.logistica_alimentos.estoque.api.dto;

import br.com.logistica_alimentos.estoque.config.Enum.TipoProduto;
import br.com.logistica_alimentos.estoque.config.Enum.UnidadeMedida;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ProdutoResponse(
        String nome,
        TipoProduto tipo,
        UnidadeMedida unidadeMedida,
        BigDecimal quantidade,
        LocalDate validade,
        String origem
) {
}
