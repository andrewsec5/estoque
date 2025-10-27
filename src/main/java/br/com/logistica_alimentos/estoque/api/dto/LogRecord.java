package br.com.logistica_alimentos.estoque.api.dto;

import java.time.LocalDateTime;

public record LogRecord(
        String tipo,
        String mensagem,
        LocalDateTime dataHora,
        String origem,
        String detalhes
) {

    public LogRecord(String tipo, String mensagem, LocalDateTime dataHora, String origem) {
        this(tipo, mensagem, dataHora, origem, null);
    }
}

