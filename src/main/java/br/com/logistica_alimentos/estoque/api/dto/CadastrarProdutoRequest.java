package br.com.logistica_alimentos.estoque.api.dto;

import br.com.logistica_alimentos.estoque.config.Enum.TipoProduto;
import br.com.logistica_alimentos.estoque.config.Enum.UnidadeMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarProdutoRequest(
        @NotBlank String nome,
        @NotNull TipoProduto tipo,
        @NotNull UnidadeMedida unidadeMedida
) {
}
