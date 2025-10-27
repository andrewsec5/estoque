package br.com.logistica_alimentos.estoque.config;

import br.com.logistica_alimentos.estoque.config.anotacao.LogSucesso;
import br.com.logistica_alimentos.estoque.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstoqueScheduler {

    private final ProdutoService produtoService;

    @LogSucesso(mensagem = "Contagem programada realizada com sucesso")
    @Scheduled(cron = "0 0 6 * * *")
    public void gerarRelatorioContagem(){
            produtoService.contagemProdutos();
    }

    @LogSucesso(mensagem = "Verificação de validade programada realizada com sucesso")
    @Scheduled(cron = "0 0 6 * * *")
    public void gerarRelatorioValidade(){
        produtoService.contagemProdutos();
    }

}
