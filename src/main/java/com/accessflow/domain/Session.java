package com.accessflow.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Session domain entity representing an active authentication session.
 * Pure POJO with no framework dependencies - follows Clean Architecture principles.
 */
public class Session {

    private UUID id;
    private String token;
    private User user;
    private Instant expiresAt;
    private boolean isRevoked;
    private String ipAddress;
    private String userAgent;
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Creates a new session for a user with the given token and expiry time.
     */
    public Session(UUID id, User user, String token, Instant expiresAt, Instant now) {
        if (id == null) {
            throw new IllegalArgumentException("Session ID cannot be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        if (expiresAt == null) {
            throw new IllegalArgumentException("Expiry time cannot be null");
        }
        if (now == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        if (expiresAt.isBefore(now)) {
            throw new IllegalArgumentException("Expiry time cannot be in the past");
        }

        this.id = id;
        this.user = user;
        this.token = token;
        this.expiresAt = expiresAt;
        this.isRevoked = false;
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Reconstitutes a session from persistence (used by repositories).
     */
    public Session(UUID id, String token, User user, Instant expiresAt, boolean isRevoked,
                   String ipAddress, String userAgent, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiresAt = expiresAt;
        this.isRevoked = isRevoked;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public boolean isRevoked() {
        return isRevoked;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Checks if this session is currently valid.
     * A session is valid if it's not revoked and hasn't expired.
     */
    public boolean isValid(Instant now) {
        if (now == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        return !isRevoked && now.isBefore(expiresAt);
    }

    /**
     * Checks if this session has expired.
     */
    public boolean isExpired(Instant now) {
        if (now == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        return now.isAfter(expiresAt);
    }

    /**
     * Revokes this session, making it invalid.
     */
    public void revoke(Instant now) {
        if (now == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        this.isRevoked = true;
        this.updatedAt = now;
    }

    /**
     * Extends the session expiry time.
     */
    public void extendExpiry(Instant newExpiryTime, Instant now) {
        if (newExpiryTime == null) {
            throw new IllegalArgumentException("New expiry time cannot be null");
        }
        if (now == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        if (!newExpiryTime.isAfter(this.expiresAt)) {
            throw new IllegalArgumentException("New expiry time must be after current expiry time");
        }
        this.expiresAt = newExpiryTime;
        this.updatedAt = now;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
