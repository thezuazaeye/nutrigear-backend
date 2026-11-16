package com.nutrigear.nutrigear_backend.service;

import com.nutrigear.nutrigear_backend.exception.ApiException;
import com.nutrigear.nutrigear_backend.model.Producto;
import com.nutrigear.nutrigear_backend.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Capa de servicio: contiene la lógica del negocio.
 * El controlador recibe la petición HTTP y llama a este servicio; este usa el repositorio.
 */
@Service
public class ProductoService {

    private final ProductoRepository repositorio;

    // Inyección por constructor: Spring entrega el repositorio automáticamente
    public ProductoService(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    // READ (lista): todos los productos, o solo los de una categoría si se indica
    public List<Producto> listar(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            return repositorio.findAll();
        }
        return repositorio.findByCategoriaIgnoreCase(categoria.trim());
    }

    // READ (uno): si no existe, responde 404
    public Producto obtener(Long id) {
        return repositorio.findById(id).orElseThrow(
                () -> new ApiException(HttpStatus.NOT_FOUND, "Producto no encontrado con id " + id));
    }

    // CREATE: se fuerza id = null para que la base de datos asigne uno nuevo
    public Producto crear(Producto producto) {
        producto.setId(null);
        return repositorio.save(producto);
    }

    // UPDATE: se busca el existente y se copian los campos nuevos encima
    public Producto actualizar(Long id, Producto datos) {
        Producto existente = obtener(id);
        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio(datos.getPrecio());
        existente.setCategoria(datos.getCategoria());
        existente.setStock(datos.getStock());
        existente.setImagen(datos.getImagen());
        return repositorio.save(existente);
    }

    // DELETE: primero verifica que exista (si no, 404)
    public void eliminar(Long id) {
        Producto existente = obtener(id);
        repositorio.delete(existente);
    }
}
