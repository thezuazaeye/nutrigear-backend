package com.nutrigear.nutrigear_backend.service;

import com.nutrigear.nutrigear_backend.dto.ContactoRequest;
import com.nutrigear.nutrigear_backend.exception.ApiException;
import com.nutrigear.nutrigear_backend.model.MensajeContacto;
import com.nutrigear.nutrigear_backend.repository.MensajeContactoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactoService {

    private final MensajeContactoRepository repositorio;

    public ContactoService(MensajeContactoRepository repositorio) {
        this.repositorio = repositorio;
    }

    // CREATE: convierte el DTO en entidad y agrega la fecha del servidor
    public MensajeContacto crear(ContactoRequest datos) {
        MensajeContacto mensaje = new MensajeContacto();
        mensaje.setNombre(datos.nombre().trim());
        mensaje.setEmail(datos.email().trim().toLowerCase());
        mensaje.setComentario(datos.comentario().trim());
        mensaje.setFecha(LocalDateTime.now());
        return repositorio.save(mensaje);
    }

    // READ (lista): del más reciente al más antiguo
    public List<MensajeContacto> listar() {
        return repositorio.findAllByOrderByFechaDesc();
    }

    // READ (uno): 404 si no existe
    public MensajeContacto obtener(Long id) {
        return repositorio.findById(id).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "Mensaje no encontrado con id " + id));
    }
}
