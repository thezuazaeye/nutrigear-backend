package com.nutrigear.nutrigear_backend.repository;

import com.nutrigear.nutrigear_backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio: acceso a la tabla "productos".
 * Al extender JpaRepository, Spring genera solo findAll, findById, save, delete, count...
 * Los métodos "findBy..." se generan a partir de su nombre (no hay que escribir SQL).
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // SELECT * FROM productos WHERE LOWER(categoria) = LOWER(?)
    List<Producto> findByCategoriaIgnoreCase(String categoria);
}
