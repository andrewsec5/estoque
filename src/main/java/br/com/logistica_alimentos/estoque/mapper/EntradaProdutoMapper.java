package br.com.logistica_alimentos.estoque.mapper;

import br.com.logistica_alimentos.estoque.api.dto.EntradaProdutoRequest;
import br.com.logistica_alimentos.estoque.api.dto.EntradaProdutoResponse;
import br.com.logistica_alimentos.estoque.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntradaProdutoMapper {

    EntradaProdutoMapper INSTANCE = Mappers.getMapper(EntradaProdutoMapper.class);

    @Mapping(target="id", ignore=true)
    Produto toEntity(EntradaProdutoRequest request);

    @Mapping(target = "dataEntrada", expression = "java(java.time.LocalDateTime.now())")
    EntradaProdutoResponse toResponse(Produto produto);
}
