package com.nutrigear.nutrigear_backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Captura las excepciones lanzadas en cualquier controlador y las convierte
 * en respuestas JSON ordenadas, en vez de mostrar un error técnico al cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Errores nuestros: producto no encontrado (404), correo repetido (409), etc.
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> manejarApi(ApiException ex) {
        ErrorResponse cuerpo = new ErrorResponse(
                ex.getEstado().value(), ex.getMessage(), null, LocalDateTime.now());
        return ResponseEntity.status(ex.getEstado()).body(cuerpo);
    }

    // Falla una validación (@NotBlank, @Min, @Email...) en el cuerpo recibido: 400 con el detalle por campo
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        ErrorResponse cuerpo = new ErrorResponse(400, "Datos inválidos", errores, LocalDateTime.now());
        return ResponseEntity.badRequest().body(cuerpo);
    }

    // El JSON enviado está mal formado o falta el cuerpo: 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        ErrorResponse cuerpo = new ErrorResponse(
                400, "El cuerpo de la petición no es un JSON válido", null, LocalDateTime.now());
        return ResponseEntity.badRequest().body(cuerpo);
    }
}
