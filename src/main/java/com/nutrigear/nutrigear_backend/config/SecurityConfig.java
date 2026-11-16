package com.nutrigear.nutrigear_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Configuración de Spring Security (versión inicial).
 * En este paso solo necesitamos el codificador BCrypt. Las rutas siguen abiertas;
 * se protegerán con JWT en el paso 4.
 */
@Configuration
public class SecurityConfig {

    // BCrypt: algoritmo de hash lento y con "sal" aleatoria, hecho para contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filtroDeSeguridad(HttpSecurity http) throws Exception {
        http
                // CSRF protege sesiones con cookies; como usaremos tokens en un header, se desactiva
                .csrf(csrf -> csrf.disable())
                // STATELESS: el servidor no guarda sesión entre peticiones
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Por ahora todo permitido (temporal)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
