package com.nutrigear.nutrigear_backend.repository;

import com.nutrigear.nutrigear_backend.model.MensajeContacto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeContactoRepository extends JpaRepository<MensajeContacto, Long> {

    // Todos los mensajes, del más reciente al más antiguo
    List<MensajeContacto> findAllByOrderByFechaDesc();
}
