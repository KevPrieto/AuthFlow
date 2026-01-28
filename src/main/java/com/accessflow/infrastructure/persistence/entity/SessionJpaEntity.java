package com.accessflow.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * Session entity representing an active authentication session.
 * Each session is associated with a JWT token and has an expiry time.
 */
@Entity
@Table(name = "sessions", indexes = {
    @Index(name = "idx_session_token", columnList = "token", unique = true),
    @Index(name = "idx_session_user", columnList = "user_id"),
    @Index(name = "idx_session_expiry", columnList = "expires_at")
})
public class SessionJpaEntity extends BaseJpaEntity {

    @Column(name = "token", nullable = false, unique = true, length = 500)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_session_user"))
    private UserJpaEntity user;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "is_revoked", nullable = false)
    private boolean isRevoked;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    public SessionJpaEntity() {
    }

    /**
     * Creates a new session for a user with the given token and expiry time.
     */
    public SessionJpaEntity(UserJpaEntity user, String token, Instant expiresAt) {
        this.user = user;
        this.token = token;
        this.expiresAt = expiresAt;
        this.isRevoked = false;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isRevoked() {
        return isRevoked;
    }

    public void setRevoked(boolean revoked) {
        isRevoked = revoked;
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

    /**
     * Checks if this session is currently valid.
     * A session is valid if it's not revoked and hasn't expired.
     */
    public boolean isValid() {
        return !isRevoked && Instant.now().isBefore(expiresAt);
    }

    /**
     * Checks if this session has expired.
     */
    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    /**
     * Revokes this session, making it invalid.
     */
    public void revoke() {
        this.isRevoked = true;
    }

    /**
     * Extends the session expiry time.
     */
    public void extendExpiry(Instant newExpiryTime) {
        if (newExpiryTime.isAfter(this.expiresAt)) {
            this.expiresAt = newExpiryTime;
        } else {
            throw new IllegalArgumentException("New expiry time must be after current expiry time");
        }
    }
}
