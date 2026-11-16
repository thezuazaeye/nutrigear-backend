package com.nutrigear.nutrigear_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro que se ejecuta en CADA petición antes de llegar al controlador.
 * Si la petición trae "Authorization: Bearer <token>" y el token es válido,
 * deja registrado quién es el usuario y su rol para que Spring Security decida el acceso.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String cabecera = request.getHeader("Authorization");

        if (cabecera != null && cabecera.startsWith("Bearer ")) {
            String token = cabecera.substring(7); // quita "Bearer " (7 caracteres)
            try {
                Claims claims = jwtService.validar(token);
                String email = claims.getSubject();
                String rol = claims.get("rol", String.class);

                // Spring Security espera los roles con el prefijo "ROLE_" (hasRole("ADMIN") busca ROLE_ADMIN)
                var autenticacion = new UsernamePasswordAuthenticationToken(
                        email, null, List.of(new SimpleGrantedAuthority("ROLE_" + rol)));
                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            } catch (JwtException | IllegalArgumentException e) {
                // Token vencido, alterado o mal formado: no se autentica.
                // Si la ruta es protegida, Spring responderá 401.
            }
        }

        // Continúa con el siguiente filtro / el controlador
        filterChain.doFilter(request, response);
    }
}
