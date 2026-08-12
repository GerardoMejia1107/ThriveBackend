package com.gerardo.thrive.auth.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
                .claim("roles", userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(
                                Collectors.joining(", ")))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String extractAuthorities(String token) {
        return getClaimsFromToken(token).get("roles", String.class);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            return extractUsername(token).equals(userDetails.getUsername());
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException |
                 IllegalArgumentException | ExpiredJwtException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
