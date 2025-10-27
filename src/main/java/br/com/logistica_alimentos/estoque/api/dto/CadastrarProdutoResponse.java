package br.com.logistica_alimentos.estoque.api.dto;

import br.com.logistica_alimentos.estoque.config.Enum.TipoProduto;
import br.com.logistica_alimentos.estoque.config.Enum.UnidadeMedida;
import lombok.Builder;

@Builder
public record CadastrarProdutoResponse(
        String nome,
        TipoProduto tipo,
        UnidadeMedida unidadeMedida
) {
}
