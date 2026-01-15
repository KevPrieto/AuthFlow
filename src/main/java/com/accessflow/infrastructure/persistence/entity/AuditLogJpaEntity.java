package com.accessflow.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

/**
 * AuditLog entity representing a security audit event.
 * Tracks who did what, when, and on which resource.
 */
@Entity
@Table(name = "audit_logs", indexes = {
    @Index(name = "idx_audit_actor", columnList = "actor_id"),
    @Index(name = "idx_audit_action", columnList = "action"),
    @Index(name = "idx_audit_timestamp", columnList = "timestamp"),
    @Index(name = "idx_audit_resource", columnList = "resource_type, resource_id")
})
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "actor_id", nullable = false)
    private UUID actorId;

    @Column(name = "actor_email", nullable = false, length = 255)
    private String actorEmail;

    @Column(name = "action", nullable = false, length = 100)
    private String action;

    @Column(name = "resource_type", nullable = false, length = 50)
    private String resourceType;

    @Column(name = "resource_id")
    private UUID resourceId;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "details", length = 2000)
    private String details;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    protected AuditLog() {
    }

    /**
     * Creates a new audit log entry.
     */
    public AuditLog(UUID actorId, String actorEmail, String action, String resourceType) {
        this.actorId = actorId;
        this.actorEmail = actorEmail;
        this.action = action;
        this.resourceType = resourceType;
        this.timestamp = Instant.now();
        this.status = "SUCCESS";
    }

    public UUID getId() {
        return id;
    }

    public UUID getActorId() {
        return actorId;
    }

    public String getActorEmail() {
        return actorEmail;
    }

    public String getAction() {
        return action;
    }

    public String getResourceType() {
        return resourceType;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(UUID organizationId) {
        this.organizationId = organizationId;
    }

    public Instant getTimestamp() {
        return timestamp;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Builder for creating audit log entries with a fluent API.
     */
    public static class Builder {
        private final AuditLog auditLog;

        public Builder(UUID actorId, String actorEmail, String action, String resourceType) {
            this.auditLog = new AuditLog(actorId, actorEmail, action, resourceType);
        }

        public Builder resourceId(UUID resourceId) {
            auditLog.setResourceId(resourceId);
            return this;
        }

        public Builder organizationId(UUID organizationId) {
            auditLog.setOrganizationId(organizationId);
            return this;
        }

        public Builder ipAddress(String ipAddress) {
            auditLog.setIpAddress(ipAddress);
            return this;
        }

        public Builder userAgent(String userAgent) {
            auditLog.setUserAgent(userAgent);
            return this;
        }

        public Builder details(String details) {
            auditLog.setDetails(details);
            return this;
        }

        public Builder status(String status) {
            auditLog.setStatus(status);
            return this;
        }

        public AuditLog build() {
            return auditLog;
        }
    }
}
