package br.com.logistica_alimentos.estoque.mapper;

import br.com.logistica_alimentos.estoque.api.dto.SaidaProdutoRequest;
import br.com.logistica_alimentos.estoque.api.dto.SaidaProdutoResponse;
import br.com.logistica_alimentos.estoque.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SaidaProdutoMapper {

    SaidaProdutoMapper INSTANCE = Mappers.getMapper(SaidaProdutoMapper.class);

    @Mapping(target="id", ignore=true)
    @Mapping(target="version", ignore=true)
    @Mapping(target="motivo", ignore=true)
    Produto toEntity(SaidaProdutoRequest request);

    @Mapping(target = "dataSaida", expression = "java(java.time.LocalDateTime.now())")
    SaidaProdutoResponse toResponse(Produto produto);
}
