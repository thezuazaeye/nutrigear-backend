package com.nutrigear.nutrigear_backend.config;

import com.nutrigear.nutrigear_backend.model.Producto;
import com.nutrigear.nutrigear_backend.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Carga los productos iniciales al encender la app (los mismos del frontend).
 * Como la base H2 es temporal, se vuelve a llenar en cada arranque.
 * CommandLineRunner se ejecuta una vez, justo después de que Spring termina de iniciar.
 */
@Component
public class ProductoSeeder implements CommandLineRunner {

    private final ProductoRepository repositorio;

    public ProductoSeeder(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void run(String... args) {
        if (repositorio.count() > 0) {
            return; // si ya hay datos, no se duplican
        }
        repositorio.saveAll(List.of(
                new Producto("Proteína Whey 1 kg",
                        "Proteína de suero de leche de alta pureza. Ideal para recuperación muscular después de entrenar.",
                        29990, "Proteínas", 25, "whey"),
                new Producto("Creatina Monohidrato 300 g",
                        "Creatina monohidrato micronizada. Apoya la fuerza y el rendimiento en esfuerzos de alta intensidad.",
                        17990, "Creatina", 40, "creatina"),
                new Producto("BCAA 2:1:1 300 g",
                        "Aminoácidos ramificados en proporción 2:1:1 para acompañar entrenamientos prolongados.",
                        14990, "Aminoácidos", 30, "bcaa"),
                new Producto("Pre-entreno Explosive",
                        "Fórmula con cafeína y beta-alanina para llegar con energía a tu sesión.",
                        19990, "Pre-entreno", 18, "preentreno"),
                new Producto("Multivitamínico Deportivo",
                        "Vitaminas y minerales pensados para personas con alta carga de entrenamiento.",
                        12990, "Vitaminas", 50, "multivit"),
                new Producto("Shaker NutriGear 700 ml",
                        "Shaker con mezclador y tapa hermética. Apto para lavavajillas.",
                        6990, "Accesorios", 0, "shaker")
        ));
    }
}
