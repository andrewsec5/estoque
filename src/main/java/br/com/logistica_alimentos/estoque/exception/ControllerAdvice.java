package br.com.logistica_alimentos.estoque.exception;

import br.com.logistica_alimentos.estoque.api.dto.ApiResponse;
import br.com.logistica_alimentos.estoque.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ControllerAdvice {

    private final LogService logService;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        log.warn("Erro de negócio: {} - {}", ex.getCode(), ex.getMessage());


        logService.enviarLog(
                "ERRO_NEGOCIO",
                ex.getMessage(),
                "SERVICO_ESTOQUE",
                "Código: " + ex.getCode()
        );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getMessage(), ex.getCode()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex){
        log.warn("Erro de requisição: {}", ex.getMessage());


        logService.enviarLog(
                "ERRO_REQUISICAO",
                ex.getMessage(),
                "SERVICO_ESTOQUE"
        );


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>("error", ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.warn("Erro de interno: {} - {}", ex.getStackTrace(), ex.getMessage());


        logService.enviarLog(
                "ERRO_TECNICO",
                ex.getMessage(),
                "SERVICO_ESTOQUE"
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("INTERNAL_ERROR", "Ocorreu um erro inesperado"));
    }

}
