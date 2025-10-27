package br.com.logistica_alimentos.estoque.model;

import br.com.logistica_alimentos.estoque.config.Enum.TipoProduto;
import br.com.logistica_alimentos.estoque.config.Enum.UnidadeMedida;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "catalogo")
public class Catalogo {

    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private String nome;

    @NotNull
    private TipoProduto tipo;

    @NotNull
    private UnidadeMedida unidadeMedida;
}
