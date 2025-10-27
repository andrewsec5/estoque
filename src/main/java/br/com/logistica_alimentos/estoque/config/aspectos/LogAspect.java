package br.com.logistica_alimentos.estoque.config.aspectos;

import br.com.logistica_alimentos.estoque.config.anotacao.LogSucesso;
import br.com.logistica_alimentos.estoque.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LogAspect {

    private final LogService logService;

    @Around("@annotation(logSucesso)")
    public Object logExecucaoComTempo(ProceedingJoinPoint joinPoint, LogSucesso logSucesso) throws Throwable {

        long inicio = System.currentTimeMillis();
        String nomeMetodo = joinPoint.getSignature().toShortString();
        String origem = logSucesso.origem();

        try {
            Object retorno = joinPoint.proceed();

            long duracao = System.currentTimeMillis() - inicio;

            String mensagem = logSucesso.mensagem().isEmpty()
                    ? "Método executado com sucesso: " + nomeMetodo
                    : logSucesso.mensagem();

            log.info("✅ {} | Tempo de execução: {} ms | Origem: {}", mensagem, duracao, origem);

            logService.enviarLog("SUCESSO", mensagem + " | Duração: " + duracao + "ms", origem);

            return retorno;

        } catch (Exception ex) {

            long duracao = System.currentTimeMillis() - inicio;

            log.error("❌ Erro ao executar método: {} | Tempo até falha: {} ms | Erro: {}",
                    nomeMetodo, duracao, ex.getMessage(), ex);

            logService.enviarLog("ERRO", "Falha ao executar " + nomeMetodo + " (" + duracao + "ms): " + ex.getMessage(), origem);

            throw ex;
        }
    }
}
