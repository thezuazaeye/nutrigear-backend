package com.nutrigear.nutrigear_backend.config;

import com.nutrigear.nutrigear_backend.model.Rol;
import com.nutrigear.nutrigear_backend.model.Usuario;
import com.nutrigear.nutrigear_backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Crea 3 usuarios de DEMOSTRACIÓN (los mismos del frontend) para poder probar los roles.
 * Sus claves se guardan igualmente con hash BCrypt.
 * Antes de un despliegue real se deben eliminar o leer desde variables de entorno.
 */
@Component
public class UsuarioSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder codificador;

    public UsuarioSeeder(UsuarioRepository usuarios, PasswordEncoder codificador) {
        this.usuarios = usuarios;
        this.codificador = codificador;
    }

    @Override
    public void run(String... args) {
        crearSiNoExiste("Administrador", "NutriGear", "admin@nutrigear.cl", "admin123", Rol.ADMIN);
        crearSiNoExiste("Vendedor", "NutriGear", "vendedor@nutrigear.cl", "vende123", Rol.VENDEDOR);
        crearSiNoExiste("Cliente", "de Prueba", "cliente@nutrigear.cl", "cliente123", Rol.CLIENTE);
    }

    private void crearSiNoExiste(String nombre, String apellidos, String email, String clave, Rol rol) {
        if (usuarios.existsByEmail(email)) {
            return;
        }
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setApellidos(apellidos);
        u.setEmail(email);
        u.setPassword(codificador.encode(clave));
        u.setRol(rol);
        usuarios.save(u);
    }
}
