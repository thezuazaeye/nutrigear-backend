package com.nutrigear.nutrigear_backend.service;

import com.nutrigear.nutrigear_backend.dto.AuthResponse;
import com.nutrigear.nutrigear_backend.dto.LoginRequest;
import com.nutrigear.nutrigear_backend.dto.RegistroRequest;
import com.nutrigear.nutrigear_backend.dto.UsuarioResponse;
import com.nutrigear.nutrigear_backend.exception.ApiException;
import com.nutrigear.nutrigear_backend.model.Rol;
import com.nutrigear.nutrigear_backend.model.Usuario;
import com.nutrigear.nutrigear_backend.repository.UsuarioRepository;
import com.nutrigear.nutrigear_backend.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Lógica de registro e inicio de sesión (ahora el login entrega un JWT). */
@Service
public class AuthService {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder codificador;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarios, PasswordEncoder codificador, JwtService jwtService) {
        this.usuarios = usuarios;
        this.codificador = codificador;
        this.jwtService = jwtService;
    }

    public UsuarioResponse registrar(RegistroRequest datos) {
        String email = datos.email().trim().toLowerCase();

        if (usuarios.existsByEmail(email)) {
            throw new ApiException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(datos.nombre().trim());
        usuario.setApellidos(datos.apellidos().trim());
        usuario.setEmail(email);
        usuario.setDireccion(datos.direccion());
        usuario.setPassword(codificador.encode(datos.password()));
        usuario.setRol(Rol.CLIENTE);

        return UsuarioResponse.desde(usuarios.save(usuario));
    }

    public AuthResponse iniciarSesion(LoginRequest datos) {
        String email = datos.email().trim().toLowerCase();

        Usuario usuario = usuarios.findByEmail(email).orElseThrow(
                () -> new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (!codificador.matches(datos.password(), usuario.getPassword())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        // Credenciales correctas: se genera el JWT y se entrega junto a los datos del usuario
        String token = jwtService.generarToken(usuario);
        return new AuthResponse(token, "Bearer", UsuarioResponse.desde(usuario));
    }
}
