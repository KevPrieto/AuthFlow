package com.accessflow.domain;

import java.time.Instant;
import java.util.*;

/**
 * Role domain entity representing a named set of permissions.
 * Pure POJO with no framework dependencies - follows Clean Architecture principles.
 */
public class Role {

    private UUID id;
    private String name;
    private String description;
    private boolean isSystemRole;
    private final Set<Permission> permissions;
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Creates a new role with the given name.
     */
    public Role(UUID id, String name, Instant now) {
        this(id, name, false, now);
    }

    /**
     * Creates a new role with the given name and system role flag.
     * System roles cannot be deleted or renamed.
     */
    public Role(UUID id, String name, boolean isSystemRole, Instant now) {
        if (id == null) {
            throw new IllegalArgumentException("Role ID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }
        if (now == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        this.id = id;
        this.name = name.trim();
        this.isSystemRole = isSystemRole;
        this.permissions = new HashSet<>();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Reconstitutes a role from persistence (used by repositories).
     */
    public Role(UUID id, String name, String description, boolean isSystemRole,
                Set<Permission> permissions, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isSystemRole = isSystemRole;
        this.permissions = permissions != null ? new HashSet<>(permissions) : new HashSet<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(String newName, Instant now) {
        if (this.isSystemRole) {
            throw new IllegalStateException("System role names cannot be changed");
        }
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }
        this.name = newName.trim();
        this.updatedAt = now;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSystemRole() {
        return isSystemRole;
    }

    /**
     * Returns an unmodifiable view of permissions.
     * Prevents external modification of internal state.
     */
    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    /**
     * Adds a permission to this role.
     */
    public void addPermission(Permission permission, Instant now) {
        if (permission == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }
        this.permissions.add(permission);
        this.updatedAt = now;
    }

    /**
     * Removes a permission from this role.
     */
    public void removePermission(Permission permission, Instant now) {
        if (permission == null) {
            throw new IllegalArgumentException("Permission cannot be null");
        }
        this.permissions.remove(permission);
        this.updatedAt = now;
    }

    /**
     * Checks if this role has a specific permission.
     */
    public boolean hasPermission(Permission permission) {
        return this.permissions.contains(permission);
    }

    /**
     * Checks if this role has a permission by key.
     */
    public boolean hasPermission(String permissionKey) {
        if (permissionKey == null) {
            return false;
        }
        return this.permissions.stream()
            .anyMatch(p -> p.getKey().equals(permissionKey));
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
