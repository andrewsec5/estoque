package br.com.logistica_alimentos.estoque.mapper;

import br.com.logistica_alimentos.estoque.api.dto.DescarteProdutoRequest;
import br.com.logistica_alimentos.estoque.api.dto.DescarteProdutoResponse;
import br.com.logistica_alimentos.estoque.model.Descarte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DescarteProdutoMapper {

    DescarteProdutoMapper INSTANCE = Mappers.getMapper(DescarteProdutoMapper.class);

    @Mapping(target="id", ignore=true)
    @Mapping(target="version", ignore=true)
    Descarte toEntity(DescarteProdutoRequest request);

    @Mapping(target = "dataDescarte", expression = "java(java.time.LocalDateTime.now())")
    DescarteProdutoResponse toResponse(Descarte produto);
}
