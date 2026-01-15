package com.accessflow.application.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO for authentication response containing JWT token and user info.
 */
public class AuthResponse {

    private String token;
    private String tokenType = "Bearer";
    private Instant expiresAt;
    private UserDto user;

    public AuthResponse() {
    }

    public AuthResponse(String token, Instant expiresAt, UserDto user) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
}
