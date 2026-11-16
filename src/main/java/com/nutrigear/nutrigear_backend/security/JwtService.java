package com.nutrigear.nutrigear_backend.security;

import com.nutrigear.nutrigear_backend.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Crea y valida tokens JWT.
 * Un JWT tiene 3 partes: cabecera.contenido(claims).firma. La firma, hecha con nuestra clave secreta,
 * garantiza que nadie pueda modificar el contenido sin que lo detectemos.
 */
@Service
public class JwtService {

    private final SecretKey clave;
    private final long expiracionMs;

    // @Value lee las propiedades de application.properties (que a su vez leen la variable JWT_SECRET)
    public JwtService(@Value("${app.jwt.secret}") String secretoBase64,
                      @Value("${app.jwt.expiration-ms}") long expiracionMs) {
        this.clave = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretoBase64));
        this.expiracionMs = expiracionMs;
    }

    // Genera el token de un usuario: subject = correo, claim "rol", fecha de emisión y de vencimiento
    public String generarToken(Usuario usuario) {
        Date ahora = new Date();
        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("rol", usuario.getRol().name())
                .issuedAt(ahora)
                .expiration(new Date(ahora.getTime() + expiracionMs))
                .signWith(clave)
                .compact();
    }

    // Verifica la firma y la fecha de vencimiento. Si algo falla, lanza una JwtException.
    public Claims validar(String token) {
        return Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
