package br.com.logistica_alimentos.estoque.mapper;

import br.com.logistica_alimentos.estoque.api.dto.AjusteManualResponse;
import br.com.logistica_alimentos.estoque.model.Catalogo;
import br.com.logistica_alimentos.estoque.model.Produto;
import br.com.logistica_alimentos.estoque.repository.CatalogoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AjusteManualMapper {

    private final CatalogoRepository catalogoRepository;

    public AjusteManualResponse toResponse(Produto produto, String motivo){
        Catalogo catalogo = catalogoRepository.findByNome(produto.getNome());

        return AjusteManualResponse.builder()
                .nome(produto.getNome())
                .tipo(catalogo.getTipo())
                .quantidade(produto.getQuantidade())
                .unidadeMedida(catalogo.getUnidadeMedida())
                .validade(produto.getValidade())
                .origem(produto.getOrigem())
                .motivo(motivo)
                .build();
    }
}
