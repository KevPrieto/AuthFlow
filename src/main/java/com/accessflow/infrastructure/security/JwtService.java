package com.accessflow.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service for JWT token generation and validation.
 * Infrastructure layer - framework-specific implementation.
 */
@Service
public class JwtService {

    private final String secret;
    private final long expirationMs;
    private final String issuer;
    private final Key signingKey;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMs,
            @Value("${jwt.issuer}") String issuer) {
        this.secret = secret;
        this.expirationMs = expirationMs;
        this.issuer = issuer;
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Generates a JWT token for a user.
     */
    public String generateToken(UUID userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId.toString());
        claims.put("email", email);

        Instant now = Instant.now();
        Instant expiration = now.plusMillis(expirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts user ID from token.
     */
    public UUID extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        String userIdString = claims.get("userId", String.class);
        return UUID.fromString(userIdString);
    }

    /**
     * Extracts email from token.
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extracts expiration time from token.
     */
    public Instant extractExpiration(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.toInstant();
    }

    /**
     * Validates a token.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if token is expired.
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(Instant.now());
    }

    /**
     * Gets token expiration time in Instant.
     */
    public Instant getExpirationTime() {
        return Instant.now().plusMillis(expirationMs);
    }

    /**
     * Extracts all claims from token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
