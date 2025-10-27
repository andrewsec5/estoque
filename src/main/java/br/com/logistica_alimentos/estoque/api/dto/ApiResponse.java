package br.com.logistica_alimentos.estoque.api.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiResponse<T> (

    String status,
    String message,
    String errorCode,
    T data,
    Instant timestamp
){

    public ApiResponse(String status, String message, T data){
        this(status, message, null, data, Instant.now());
    }

    public ApiResponse(String status, String message, String errorCode){
        this(status, message, errorCode, null, Instant.now());
    }

    public static <T> ApiResponse<T> success(String message, T data){
        return new ApiResponse<>("success", message, data);
    }

    public static <T> ApiResponse<T> error(String message, String errorCode){
        return new ApiResponse<>("error", message, errorCode);
    }

}
