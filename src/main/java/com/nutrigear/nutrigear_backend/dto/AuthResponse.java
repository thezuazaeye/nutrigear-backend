package com.nutrigear.nutrigear_backend.dto;

/**
 * Respuesta del login: el token JWT + los datos del usuario.
 * El frontend guardará "token" en localStorage y lo enviará en cada petición protegida.
 */
public record AuthResponse(
        String token,
        String tipo,
        UsuarioResponse usuario
) {
}
