package com.nutrigear.nutrigear_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) de entrada para el registro.
 * Se usa un DTO y no la entidad Usuario para que el cliente NO pueda enviar "rol" ni "id":
 * el rol lo decide siempre el servidor.
 */
public record RegistroRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 80, message = "El nombre no puede superar 80 caracteres")
        String nombre,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(max = 80, message = "Los apellidos no pueden superar 80 caracteres")
        String apellidos,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        @Size(max = 120, message = "El correo no puede superar 120 caracteres")
        String email,

        // Mismo mínimo que el frontend (4). El máximo 72 es el límite real de BCrypt.
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 4, max = 72, message = "La contraseña debe tener entre 4 y 72 caracteres")
        String password,

        @Size(max = 200, message = "La dirección no puede superar 200 caracteres")
        String direccion
) {
}
