package com.nutrigear.nutrigear_backend.repository;

import com.nutrigear.nutrigear_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // SELECT * FROM usuarios WHERE email = ?  (Optional evita trabajar con null)
    Optional<Usuario> findByEmail(String email);

    // SELECT COUNT(*) > 0 FROM usuarios WHERE email = ?
    boolean existsByEmail(String email);
}
