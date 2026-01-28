package com.accessflow.infrastructure.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.security.MessageDigest;
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
    private final byte[] signingKeyBytes;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMs,
            @Value("${jwt.issuer}") String issuer) {
        this.secret = secret;
        this.expirationMs = expirationMs;
        this.issuer = issuer;
        this.signingKeyBytes = secret.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Generates a JWT token for a user.
     */
    public String generateToken(UUID userId, String email) {
        try {
            Map<String, Object> header = Map.of("alg", "HS256", "typ", "JWT");
            Map<String, Object> payload = new HashMap<>();
            payload.put("userId", userId.toString());
            payload.put("email", email);
            payload.put("sub", email);
            payload.put("iss", issuer);
            Instant now = Instant.now();
            payload.put("iat", now.getEpochSecond());
            payload.put("exp", now.plusMillis(expirationMs).getEpochSecond());

            String headerJson = MAPPER.writeValueAsString(header);
            String payloadJson = MAPPER.writeValueAsString(payload);
            String headerB = base64UrlEncode(headerJson.getBytes(StandardCharsets.UTF_8));
            String payloadB = base64UrlEncode(payloadJson.getBytes(StandardCharsets.UTF_8));
            String signingInput = headerB + "." + payloadB;
            String signature = base64UrlEncode(hmacSha256(signingInput, signingKeyBytes));
            return signingInput + "." + signature;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate token", e);
        }
    }

    /**
     * Extracts user ID from token.
     */
    public UUID extractUserId(String token) {
        Map<String, Object> claims = extractAllClaims(token);
        Object uid = claims.get("userId");
        return UUID.fromString(String.valueOf(uid));
    }

    /**
     * Extracts email from token.
     */
    public String extractEmail(String token) {
        Map<String, Object> claims = extractAllClaims(token);
        return String.valueOf(claims.get("sub"));
    }

    /**
     * Extracts expiration time from token.
     */
    public Instant extractExpiration(String token) {
        Map<String, Object> claims = extractAllClaims(token);
        Object exp = claims.get("exp");
        if (exp == null) return Instant.EPOCH;
        long epoch = Long.parseLong(String.valueOf(exp));
        return Instant.ofEpochSecond(epoch);
    }

    /**
     * Validates a token.
     */
    public boolean validateToken(String token) {
        try {
            // verify signature and expiration
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;
            String signingInput = parts[0] + "." + parts[1];
            byte[] expectedSig = hmacSha256(signingInput, signingKeyBytes);
            byte[] actualSig = Base64.getUrlDecoder().decode(parts[2]);
            if (!MessageDigest.isEqual(expectedSig, actualSig)) return false;
            Instant exp = extractExpiration(token);
            return Instant.now().isBefore(exp);
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
    private Map<String, Object> extractAllClaims(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) throw new IllegalArgumentException("Invalid JWT token");
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            return MAPPER.readValue(payloadJson, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse token claims", e);
        }
    }

    private static String base64UrlEncode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static byte[] hmacSha256(String data, byte[] key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key, "HmacSHA256"));
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }
}
