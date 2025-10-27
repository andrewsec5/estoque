package br.com.logistica_alimentos.estoque.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "produtos")
public class Produto {

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

    private String motivo;

    @Version
    private long version;

    public void subtractQuantidade(BigDecimal quantidade){
        if(this.quantidade != null && this.quantidade.compareTo(quantidade) >= 0){
            this.quantidade = this.quantidade.subtract(quantidade);
        }else{
            throw new IllegalArgumentException("Quantidade em estoque insuficiente");
        }
    }

    public void addQuantidade(BigDecimal quantidade){
        if(this.quantidade == null){
            this.quantidade = quantidade;
        }else {
            this.quantidade = this.quantidade.add(quantidade);
        }
    }

}
