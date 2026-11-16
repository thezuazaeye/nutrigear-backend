package com.nutrigear.nutrigear_backend.config;

import com.nutrigear.nutrigear_backend.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Seguridad de la API: qué rutas son públicas, cuáles exigen token y qué rol necesitan.
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFiltro;
    private final String[] origenesPermitidos;

    public SecurityConfig(JwtAuthenticationFilter jwtFiltro,
                          @Value("${app.cors.allowed-origins}") String[] origenesPermitidos) {
        this.jwtFiltro = jwtFiltro;
        this.origenesPermitidos = origenesPermitidos;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filtroDeSeguridad(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults()) // usa el bean corsConfigurationSource de más abajo
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Públicas: registro/login, ver el catálogo y enviar un mensaje de contacto
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/productos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/contacto").permitAll()
                        // Solo admin o vendedor: crear/editar/eliminar productos y leer los mensajes
                        .requestMatchers("/api/v1/productos/**").hasAnyRole("ADMIN", "VENDEDOR")
                        .requestMatchers("/api/v1/contacto/**").hasAnyRole("ADMIN", "VENDEDOR")
                        // Cualquier otra ruta exige token válido
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                        // Sin token o token inválido -> 401
                        .authenticationEntryPoint((req, res, e) ->
                                escribirError(res, HttpServletResponse.SC_UNAUTHORIZED,
                                        "Debes iniciar sesión con un token válido"))
                        // Token válido pero rol insuficiente -> 403
                        .accessDeniedHandler((req, res, e) ->
                                escribirError(res, HttpServletResponse.SC_FORBIDDEN,
                                        "No tienes permisos para realizar esta acción")))
                // Nuestro filtro JWT corre antes del filtro de usuario/clave estándar
                .addFilterBefore(jwtFiltro, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // CORS: el navegador solo deja que el frontend llame a la API si esta lo autoriza explícitamente
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(origenesPermitidos));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource fuente = new UrlBasedCorsConfigurationSource();
        fuente.registerCorsConfiguration("/**", config);
        return fuente;
    }

    private void escribirError(HttpServletResponse res, int estado, String mensaje) throws IOException {
        res.setStatus(estado);
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write("{\"estado\":" + estado + ",\"mensaje\":\"" + mensaje + "\"}");
    }
}
