package com.nutrigear.nutrigear_backend.controller;

import com.nutrigear.nutrigear_backend.dto.LoginRequest;
import com.nutrigear.nutrigear_backend.dto.RegistroRequest;
import com.nutrigear.nutrigear_backend.dto.UsuarioResponse;
import com.nutrigear.nutrigear_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints públicos de autenticación: /api/v1/auth/... */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService servicio;

    public AuthController(AuthService servicio) {
        this.servicio = servicio;
    }

    // POST /api/v1/auth/registro -> crea una cuenta nueva (201 Created)
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registro(@Valid @RequestBody RegistroRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.registrar(datos));
    }

    // POST /api/v1/auth/login -> valida credenciales (200 OK, o 401 si fallan)
    @PostMapping("/login")
    public UsuarioResponse login(@Valid @RequestBody LoginRequest datos) {
        return servicio.iniciarSesion(datos);
    }
}
