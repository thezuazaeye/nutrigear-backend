package com.nutrigear.nutrigear_backend.controller;

import com.nutrigear.nutrigear_backend.dto.AuthResponse;
import com.nutrigear.nutrigear_backend.dto.LoginRequest;
import com.nutrigear.nutrigear_backend.dto.RegistroRequest;
import com.nutrigear.nutrigear_backend.dto.UsuarioResponse;
import com.nutrigear.nutrigear_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticación", description = "Registro e inicio de sesión (públicos). El login entrega el token JWT")
public class AuthController {

    private final AuthService servicio;

    public AuthController(AuthService servicio) {
        this.servicio = servicio;
    }

    // POST /api/v1/auth/registro -> crea una cuenta nueva (201 Created)
    @Operation(summary = "Registrar un usuario", description = "Crea una cuenta con rol CLIENTE")
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registro(@Valid @RequestBody RegistroRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.registrar(datos));
    }

    // POST /api/v1/auth/login -> devuelve el JWT (200 OK, o 401 si las credenciales fallan)
    @Operation(summary = "Iniciar sesión", description = "Devuelve un token JWT para usar como Bearer token")
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest datos) {
        return servicio.iniciarSesion(datos);
    }
}
