package com.accessflow.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;

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

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public UserJpaEntity getUser() { return user; }
    public void setUser(UserJpaEntity user) { this.user = user; }
    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
    public boolean isRevoked() { return isRevoked; }
    public void setRevoked(boolean revoked) { isRevoked = revoked; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
}
