package br.com.logistica_alimentos.estoque.mapper;

import br.com.logistica_alimentos.estoque.api.dto.CadastrarProdutoRequest;
import br.com.logistica_alimentos.estoque.api.dto.CadastrarProdutoResponse;
import br.com.logistica_alimentos.estoque.model.Catalogo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CadastrarProdutoMapper {

    CadastrarProdutoMapper INSTANCE = Mappers.getMapper(CadastrarProdutoMapper.class);

    @Mapping(target="id", ignore=true)
    Catalogo toEntity(CadastrarProdutoRequest request);

    CadastrarProdutoResponse toResponse(Catalogo produto);
}
