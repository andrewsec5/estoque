package br.com.logistica_alimentos.estoque.repository;

import br.com.logistica_alimentos.estoque.model.Catalogo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CatalogoRepository extends MongoRepository<Catalogo, String> {
    Catalogo findByNome(String nome);
    boolean existsByNome(String nome);
}
