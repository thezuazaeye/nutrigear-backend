package com.nutrigear.nutrigear_backend.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Formato único de los errores que devuelve la API.
 * Un "record" es una clase inmutable con constructor y getters generados automáticamente.
 * @JsonInclude(NON_NULL) hace que los campos null (por ejemplo "errores") no aparezcan en el JSON.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        int estado,
        String mensaje,
        Map<String, String> errores,
        LocalDateTime fecha
) {
}
