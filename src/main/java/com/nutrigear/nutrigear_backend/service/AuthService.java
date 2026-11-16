package com.nutrigear.nutrigear_backend.service;

import com.nutrigear.nutrigear_backend.dto.LoginRequest;
import com.nutrigear.nutrigear_backend.dto.RegistroRequest;
import com.nutrigear.nutrigear_backend.dto.UsuarioResponse;
import com.nutrigear.nutrigear_backend.exception.ApiException;
import com.nutrigear.nutrigear_backend.model.Rol;
import com.nutrigear.nutrigear_backend.model.Usuario;
import com.nutrigear.nutrigear_backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Lógica de registro e inicio de sesión. */
@Service
public class AuthService {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder codificador; // BCrypt, definido en SecurityConfig

    public AuthService(UsuarioRepository usuarios, PasswordEncoder codificador) {
        this.usuarios = usuarios;
        this.codificador = codificador;
    }

    public UsuarioResponse registrar(RegistroRequest datos) {
        // Normalizamos el correo para que "Ana@Gmail.com" y "ana@gmail.com" sean el mismo
        String email = datos.email().trim().toLowerCase();

        if (usuarios.existsByEmail(email)) {
            throw new ApiException(HttpStatus.CONFLICT, "El correo ya está registrado"); // 409
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(datos.nombre().trim());
        usuario.setApellidos(datos.apellidos().trim());
        usuario.setEmail(email);
        usuario.setDireccion(datos.direccion());
        // Se guarda el HASH, nunca la contraseña en texto plano
        usuario.setPassword(codificador.encode(datos.password()));
        // Quien se registra por la web SIEMPRE es cliente (no se puede elegir ser admin)
        usuario.setRol(Rol.CLIENTE);

        return UsuarioResponse.desde(usuarios.save(usuario));
    }

    public UsuarioResponse iniciarSesion(LoginRequest datos) {
        String email = datos.email().trim().toLowerCase();

        // Mismo mensaje si el correo no existe o si la clave es incorrecta:
        // así un atacante no puede averiguar qué correos están registrados.
        Usuario usuario = usuarios.findByEmail(email).orElseThrow(
                () -> new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        // matches compara la clave recibida contra el hash guardado
        if (!codificador.matches(datos.password(), usuario.getPassword())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        return UsuarioResponse.desde(usuario);
    }
}
