package com.nutrigear.nutrigear_backend.controller;

import com.nutrigear.nutrigear_backend.dto.ContactoRequest;
import com.nutrigear.nutrigear_backend.model.MensajeContacto;
import com.nutrigear.nutrigear_backend.service.ContactoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Flujo de contacto: /api/v1/contacto */
@RestController
@RequestMapping("/api/v1/contacto")
@Tag(name = "Contacto", description = "Formulario de contacto. Enviar es público; leer mensajes requiere rol ADMIN o VENDEDOR")
public class ContactoController {

    private final ContactoService servicio;

    public ContactoController(ContactoService servicio) {
        this.servicio = servicio;
    }

    // POST: cualquier visitante puede enviar un mensaje (201 Created)
    @Operation(summary = "Enviar un mensaje de contacto")
    @PostMapping
    public ResponseEntity<MensajeContacto> enviar(@Valid @RequestBody ContactoRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crear(datos));
    }

    // GET: ver los mensajes recibidos (restringido a admin/vendedor por SecurityConfig)
    @Operation(summary = "Listar mensajes recibidos", description = "Requiere token con rol ADMIN o VENDEDOR")
    @GetMapping
    public List<MensajeContacto> listar() {
        return servicio.listar();
    }

    @Operation(summary = "Obtener un mensaje por id", description = "Requiere token con rol ADMIN o VENDEDOR")
    @GetMapping("/{id}")
    public MensajeContacto obtener(@PathVariable Long id) {
        return servicio.obtener(id);
    }
}
