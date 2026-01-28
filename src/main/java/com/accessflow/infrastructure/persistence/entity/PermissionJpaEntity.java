package com.accessflow.infrastructure.persistence.entity;

import jakarta.persistence.*;

/**
 * Permission entity representing a granular access right.
 * Permissions are assigned to roles, which are then assigned to users.
 */
@Entity
@Table(name = "permissions", indexes = {
    @Index(name = "idx_permission_key", columnList = "permission_key", unique = true)
})
public class PermissionJpaEntity extends BaseJpaEntity {

    @Column(name = "permission_key", nullable = false, unique = true, length = 100)
    private String key;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "resource", length = 100)
    private String resource;

    @Column(name = "action", length = 50)
    private String action;

    public PermissionJpaEntity() {
    }

    /**
     * Creates a new permission with the given key.
     * Key should follow the format: resource:action (e.g., "users:create", "orgs:read")
     */
    public PermissionJpaEntity(String key) {
        this.key = key;
        parseKeyComponents();
    }

    /**
     * Creates a new permission with the given key and description.
     */
    public PermissionJpaEntity(String key, String description) {
        this.key = key;
        this.description = description;
        parseKeyComponents();
    }

    /**
     * Parses the permission key to extract resource and action components.
     * Format: "resource:action"
     */
    private void parseKeyComponents() {
        String[] parts = key.split(":", 2);
        if (parts.length == 2) {
            this.resource = parts[0];
            this.action = parts[1];
        } else {
            this.resource = key;
            this.action = "access";
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        parseKeyComponents();
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

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Checks if this permission applies to a specific resource and action.
     */
    public boolean matches(String resource, String action) {
        return this.resource.equals(resource) && this.action.equals(action);
    }

    /**
     * Checks if this permission is a wildcard for all actions on a resource.
     */
    public boolean isWildcard() {
        return "*".equals(action);
    }
}
