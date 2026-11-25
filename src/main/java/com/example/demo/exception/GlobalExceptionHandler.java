package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String error, String message) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                error,
                message);
        return ResponseEntity.status(status).body(body);
    }

    // 404 Not Found — Item ausente ou indisponível no inventário
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFound(ItemNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Item Not Found", ex.getMessage());
    }

    // 404 Not Found — Personagem ou inimigo inexistente
    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCharacterNotFound(CharacterNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Character Not Found", ex.getMessage());
    }

    // 400 Bad Request — Vida insuficiente (ou mana/energia insuficiente) para agir
    @ExceptionHandler(InsufficientHealthException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientHealth(InsufficientHealthException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Insufficient Health", ex.getMessage());
    }

    // 400 Bad Request — Escolha inválida ou tentativa de avançar em nó folha da
    // árvore
    @ExceptionHandler(InvalidDecisionException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDecision(InvalidDecisionException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid Decision", ex.getMessage());
    }

    // 400 Bad Request — Regras de estado inválido (ex.: inventário cheio, quest não
    // iniciada)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid State", ex.getMessage());
    }

    // 400 Bad Request — Argumentos inválidos na requisição ou entidade
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Error", ex.getMessage());
    }
}
