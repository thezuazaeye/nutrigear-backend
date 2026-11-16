package com.nutrigear.nutrigear_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Datos que envía el formulario de contacto (mismos campos del formulario del frontend). */
public record ContactoRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        @Size(max = 120, message = "El correo no puede superar 120 caracteres")
        String email,

        @NotBlank(message = "El comentario es obligatorio")
        @Size(max = 1000, message = "El comentario no puede superar 1000 caracteres")
        String comentario
) {
}
