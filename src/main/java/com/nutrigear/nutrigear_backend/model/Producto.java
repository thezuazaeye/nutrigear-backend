package com.nutrigear.nutrigear_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Entidad Producto: cada objeto de esta clase es una fila de la tabla "productos".
 * Los campos coinciden con los del frontend (nombre, precio, categoria, stock, imagen, descripcion).
 */
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // el id lo asigna la base de datos (1, 2, 3...)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar 120 caracteres")
    @Column(nullable = false, length = 120)
    private String nombre;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    @Column(length = 500)
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Integer precio; // pesos chilenos, sin decimales

    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false, length = 60)
    private String categoria;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

    // Clave de la imagen que usa el frontend ("whey", "creatina", ...)
    @Size(max = 60, message = "La imagen no puede superar 60 caracteres")
    @Column(length = 60)
    private String imagen;

    // JPA exige un constructor vacío
    public Producto() {
    }

    public Producto(String nombre, String descripcion, Integer precio, String categoria,
                    Integer stock, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
        this.imagen = imagen;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
}
