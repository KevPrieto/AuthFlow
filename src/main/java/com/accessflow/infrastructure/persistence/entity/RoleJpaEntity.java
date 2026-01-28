package com.accessflow.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Role entity representing a named set of permissions.
 * Roles are assigned to users within organizations (via Membership).
 */
@Entity
@Table(name = "roles", indexes = {
    @Index(name = "idx_role_name", columnList = "name", unique = true)
})
public class RoleJpaEntity extends BaseJpaEntity {

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "is_system_role", nullable = false)
    private boolean isSystemRole;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id"),
        indexes = {
            @Index(name = "idx_role_perm_role", columnList = "role_id"),
            @Index(name = "idx_role_perm_permission", columnList = "permission_id")
        }
    )
    private Set<PermissionJpaEntity> permissions = new HashSet<>();

    public RoleJpaEntity() {
    }

    /**
     * Creates a new role with the given name.
     */
    public RoleJpaEntity(String name) {
        this.name = name;
        this.isSystemRole = false;
    }

    /**
     * Creates a new system role with the given name.
     * System roles cannot be deleted.
     */
    public RoleJpaEntity(String name, boolean isSystemRole) {
        this.name = name;
        this.isSystemRole = isSystemRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setSystemRole(boolean systemRole) {
        isSystemRole = systemRole;
    }

    public Set<PermissionJpaEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionJpaEntity> permissions) {
        this.permissions = permissions;
    }

    /**
     * Adds a permission to this role.
     */
    public void addPermission(PermissionJpaEntity permission) {
        this.permissions.add(permission);
    }

    /**
     * Removes a permission from this role.
     */
    public void removePermission(PermissionJpaEntity permission) {
        this.permissions.remove(permission);
    }

    /**
     * Checks if this role has a specific permission.
     */
    public boolean hasPermission(PermissionJpaEntity permission) {
        return this.permissions.contains(permission);
    }

    /**
     * Checks if this role has a permission by key.
     */
    public boolean hasPermission(String permissionKey) {
        return this.permissions.stream()
            .anyMatch(p -> p.getKey().equals(permissionKey));
    }
}
