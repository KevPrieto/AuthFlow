package com.accessflow.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Permission domain entity representing a granular access right.
 * Pure POJO with no framework dependencies - follows Clean Architecture principles.
 */
public class Permission {

    private UUID id;
    private String key;
    private String description;
    private String resource;
    private String action;
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Creates a new permission with the given key.
     * Key should follow the format: resource:action (e.g., "users:create", "orgs:read")
     */
    public Permission(UUID id, String key, Instant now) {
        if (id == null) {
            throw new IllegalArgumentException("Permission ID cannot be null");
        }
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Permission key cannot be null or empty");
        }
        if (now == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        this.id = id;
        this.key = key.trim();
        parseKeyComponents();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Creates a new permission with the given key and description.
     */
    public Permission(UUID id, String key, String description, Instant now) {
        this(id, key, now);
        this.description = description;
    }

    /**
     * Reconstitutes a permission from persistence (used by repositories).
     */
    public Permission(UUID id, String key, String description, String resource,
                     String action, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.key = key;
        this.description = description;
        this.resource = resource;
        this.action = action;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Parses the permission key to extract resource and action components.
     * Format: "resource:action"
     */
    private void parseKeyComponents() {
        String[] parts = key.split(":", 2);
        if (parts.length == 2) {
            this.resource = parts[0].trim();
            this.action = parts[1].trim();
        } else {
            this.resource = key;
            this.action = "access";
        }
    }

    public UUID getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResource() {
        return resource;
    }

    public String getAction() {
        return action;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Checks if this permission applies to a specific resource and action.
     */
    public boolean matches(String resource, String action) {
        if (resource == null || action == null) {
            return false;
        }
        return this.resource.equals(resource) && this.action.equals(action);
    }

    /**
     * Checks if this permission is a wildcard for all actions on a resource.
     */
    public boolean isWildcard() {
        return "*".equals(action);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
