package com.nutrigear.nutrigear_backend.dto;

import com.nutrigear.nutrigear_backend.model.Rol;
import com.nutrigear.nutrigear_backend.model.Usuario;

/**
 * DTO de salida: lo que SÍ se le muestra al cliente de un usuario.
 * Observa que no tiene "password": jamás se devuelve ni siquiera el hash.
 */
public record UsuarioResponse(
        Long id,
        String nombre,
        String apellidos,
        String email,
        String direccion,
        Rol rol
) {

    // Convierte una entidad Usuario en su versión segura para enviar
    public static UsuarioResponse desde(Usuario u) {
        return new UsuarioResponse(
                u.getId(), u.getNombre(), u.getApellidos(), u.getEmail(), u.getDireccion(), u.getRol());
    }
}
