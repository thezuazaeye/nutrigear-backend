package com.nutrigear.nutrigear_backend.controller;

import com.nutrigear.nutrigear_backend.dto.ContactoRequest;
import com.nutrigear.nutrigear_backend.model.MensajeContacto;
import com.nutrigear.nutrigear_backend.service.ContactoService;
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
public class ContactoController {

    private final ContactoService servicio;

    public ContactoController(ContactoService servicio) {
        this.servicio = servicio;
    }

    // POST: cualquier visitante puede enviar un mensaje (201 Created)
    @PostMapping
    public ResponseEntity<MensajeContacto> enviar(@Valid @RequestBody ContactoRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crear(datos));
    }

    // GET: ver los mensajes recibidos (se restringirá a admin/vendedor en el paso 4)
    @GetMapping
    public List<MensajeContacto> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public MensajeContacto obtener(@PathVariable Long id) {
        return servicio.obtener(id);
    }
}
