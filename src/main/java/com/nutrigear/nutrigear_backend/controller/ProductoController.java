package com.nutrigear.nutrigear_backend.controller;

import com.nutrigear.nutrigear_backend.model.Producto;
import com.nutrigear.nutrigear_backend.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST de productos. Cada método expone un endpoint HTTP.
 * Ruta base: /api/v1/productos
 */
@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "CRUD del catálogo de NutriGear. Lectura pública; crear, editar y eliminar requieren rol ADMIN o VENDEDOR")
public class ProductoController {

    private final ProductoService servicio;

    public ProductoController(ProductoService servicio) {
        this.servicio = servicio;
    }

    // GET /api/v1/productos            -> todos
    // GET /api/v1/productos?categoria=Proteínas -> filtrados por categoría
    @Operation(summary = "Listar productos", description = "Devuelve todos los productos o filtra por categoría con ?categoria=")
    @GetMapping
    public List<Producto> listar(@RequestParam(required = false) String categoria) {
        return servicio.listar(categoria);
    }

    // GET /api/v1/productos/3 -> uno solo (@PathVariable toma el 3 desde la URL)
    @Operation(summary = "Obtener un producto por id")
    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {
        return servicio.obtener(id);
    }

    // POST /api/v1/productos -> crea (@Valid ejecuta las validaciones de la entidad). Responde 201 Created
    @Operation(summary = "Crear un producto", description = "Requiere token con rol ADMIN o VENDEDOR")
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        Producto creado = servicio.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/v1/productos/3 -> reemplaza los datos del producto 3
    @Operation(summary = "Actualizar un producto", description = "Requiere token con rol ADMIN o VENDEDOR")
    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return servicio.actualizar(id, producto);
    }

    // DELETE /api/v1/productos/3 -> elimina. Responde 204 No Content (sin cuerpo)
    @Operation(summary = "Eliminar un producto", description = "Requiere token con rol ADMIN o VENDEDOR")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
