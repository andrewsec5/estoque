package br.com.logistica_alimentos.estoque.repository;

import br.com.logistica_alimentos.estoque.config.Enum.MotivoDescarte;
import br.com.logistica_alimentos.estoque.model.Descarte;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DescarteRepository extends MongoRepository<Descarte, String> {
    boolean existsByNomeAndValidadeAndOrigemAndMotivoDescarte(String nome, LocalDate validade, String origem, MotivoDescarte motivoDescarte);
    Optional<Descarte> findByNomeAndValidadeAndOrigemAndMotivoDescarte(String nome, LocalDate validade, String origem, MotivoDescarte motivoDescarte);
}
