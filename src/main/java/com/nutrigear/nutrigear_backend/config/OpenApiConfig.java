package com.nutrigear.nutrigear_backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de la documentación Swagger (OpenAPI).
 * Define el título, la descripción y el botón "Authorize" para pegar el token JWT.
 */
@Configuration
public class OpenApiConfig {

    private static final String ESQUEMA_JWT = "bearerAuth";

    @Bean
    public OpenAPI nutrigearOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API NutriGear")
                        .version("1.0.0")
                        .description("API REST de la tienda NutriGear: catálogo de productos, "
                                + "registro/inicio de sesión con JWT y formulario de contacto.")
                        .contact(new Contact().name("Equipo NutriGear")))
                // Exige el esquema JWT en la documentación (aparece el candado en cada endpoint)
                .addSecurityItem(new SecurityRequirement().addList(ESQUEMA_JWT))
                .components(new Components().addSecuritySchemes(ESQUEMA_JWT,
                        new SecurityScheme()
                                .name(ESQUEMA_JWT)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
