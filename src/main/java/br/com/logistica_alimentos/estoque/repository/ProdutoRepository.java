package br.com.logistica_alimentos.estoque.repository;

import br.com.logistica_alimentos.estoque.model.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
    Optional<Produto> findByNomeAndValidadeAndOrigem(String nome, LocalDate validade, String origem);
    boolean existsByNomeAndValidadeAndOrigem(String nome, LocalDate validade, String origem);
}
