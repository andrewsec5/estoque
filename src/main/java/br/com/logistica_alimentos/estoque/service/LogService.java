package br.com.logistica_alimentos.estoque.service;

import br.com.logistica_alimentos.estoque.api.dto.LogRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {

    private final KafkaTemplate<String, LogRecord> kafkaTemplate;

    @Value("${app.kafka.topic.logs}")
    private String logTopic;

    public void enviarLog(String tipo, String mensagem, String origem){
        enviarLog(tipo, mensagem, origem, null);
    }

    public void enviarLog(String tipo, String mensagem, String origem, String detalhes){
        LogRecord evento = new LogRecord(tipo, mensagem, LocalDateTime.now(), origem, detalhes);
        try {
            kafkaTemplate.send(logTopic, evento);
        } catch (Exception e) {
            log.warn("Kafka indisponível. Log não enviado: {}", e.getMessage());
        }
    }
}
