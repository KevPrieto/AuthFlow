package com.accessflow.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * AuditLog domain entity representing a security audit event.
 * Tracks who did what, when, and on which resource.
 * Pure POJO with no framework dependencies - follows Clean Architecture principles.
 */
public class AuditLog {

    private UUID id;
    private UUID actorId;
    private String actorEmail;
    private String action;
    private String resourceType;
    private UUID resourceId;
    private UUID organizationId;
    private Instant timestamp;
    private String ipAddress;
    private String userAgent;
    private String details;
    private String status;

    /**
     * Creates a new audit log entry.
     * Timestamp is provided as a parameter for testability and deterministic behavior.
     */
    public AuditLog(UUID id, UUID actorId, String actorEmail, String action,
                    String resourceType, Instant timestamp) {
        if (id == null) {
            throw new IllegalArgumentException("AuditLog ID cannot be null");
        }
        if (actorId == null) {
            throw new IllegalArgumentException("Actor ID cannot be null");
        }
        if (actorEmail == null || actorEmail.isBlank()) {
            throw new IllegalArgumentException("Actor email cannot be null or empty");
        }
        if (action == null || action.isBlank()) {
            throw new IllegalArgumentException("Action cannot be null or empty");
        }
        if (resourceType == null || resourceType.isBlank()) {
            throw new IllegalArgumentException("Resource type cannot be null or empty");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        this.id = id;
        this.actorId = actorId;
        this.actorEmail = actorEmail;
        this.action = action;
        this.resourceType = resourceType;
        this.timestamp = timestamp;
        this.status = "SUCCESS";
    }

    /**
     * Reconstitutes an audit log from persistence (used by repositories).
     */
    public AuditLog(UUID id, UUID actorId, String actorEmail, String action,
                    String resourceType, UUID resourceId, UUID organizationId,
                    Instant timestamp, String ipAddress, String userAgent,
                    String details, String status) {
        this.id = id;
        this.actorId = actorId;
        this.actorEmail = actorEmail;
        this.action = action;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.organizationId = organizationId;
        this.timestamp = timestamp;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.details = details;
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditLog auditLog = (AuditLog) o;
        return Objects.equals(id, auditLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Builder for creating audit log entries with a fluent API.
     */
    public static class Builder {
        private final UUID id;
        private final UUID actorId;
        private final String actorEmail;
        private final String action;
        private final String resourceType;
        private final Instant timestamp;
        private UUID resourceId;
        private UUID organizationId;
        private String ipAddress;
        private String userAgent;
        private String details;
        private String status = "SUCCESS";

        public Builder(UUID id, UUID actorId, String actorEmail, String action,
                      String resourceType, Instant timestamp) {
            this.id = id;
            this.actorId = actorId;
            this.actorEmail = actorEmail;
            this.action = action;
            this.resourceType = resourceType;
            this.timestamp = timestamp;
        }

        public Builder resourceId(UUID resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder organizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder details(String details) {
            this.details = details;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public AuditLog build() {
            return new AuditLog(id, actorId, actorEmail, action, resourceType,
                resourceId, organizationId, timestamp, ipAddress, userAgent,
                details, status);
        }
    }
}
