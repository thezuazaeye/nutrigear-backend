package com.nutrigear.nutrigear_backend.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción propia de la API. Lleva el código HTTP que debe responder
 * (404, 409, 401, etc.) junto con un mensaje para el cliente.
 * Extiende RuntimeException para no tener que declarar "throws" en cada método.
 */
public class ApiException extends RuntimeException {

    private final HttpStatus estado;

    public ApiException(HttpStatus estado, String mensaje) {
        super(mensaje); // el mensaje queda disponible con getMessage()
        this.estado = estado;
    }

    public HttpStatus getEstado() {
        return estado;
    }
}
