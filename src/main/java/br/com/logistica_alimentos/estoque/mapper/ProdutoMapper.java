package br.com.logistica_alimentos.estoque.mapper;

import br.com.logistica_alimentos.estoque.api.dto.ProdutoResponse;
import br.com.logistica_alimentos.estoque.model.Catalogo;
import br.com.logistica_alimentos.estoque.model.Produto;
import br.com.logistica_alimentos.estoque.repository.CatalogoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoMapper {

    private final CatalogoRepository catalogoRepository;

    public ProdutoResponse toResponse(Produto produto){
        Catalogo catalogo = catalogoRepository.findByNome(produto.getNome());

        return ProdutoResponse.builder()
                .nome(produto.getNome())
                .tipo(catalogo.getTipo())
                .quantidade(produto.getQuantidade())
                .unidadeMedida(catalogo.getUnidadeMedida())
                .validade(produto.getValidade())
                .origem(produto.getOrigem())
                .build();
    }
}
