package br.com.logistica_alimentos.estoque.model;

import br.com.logistica_alimentos.estoque.config.Enum.MotivoDescarte;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "descarte")
public class Descarte {

    @Id
    private String id;

    @NotNull
    private String nome;

    @NotNull
    @Min(0)
    private BigDecimal quantidade;

    @NotNull
    private LocalDate validade;

    @NotNull
    private String origem;

    @NotNull
    private MotivoDescarte motivoDescarte;

    @Version
    private long version;

    public void addQuantidade(BigDecimal quantidade){
        if(this.quantidade == null){
            this.quantidade = quantidade;
        }else {
            this.quantidade = this.quantidade.add(quantidade);
        }
    }

}
