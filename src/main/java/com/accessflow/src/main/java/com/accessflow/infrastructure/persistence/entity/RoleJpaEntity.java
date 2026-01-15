package com.accessflow.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isSystemRole() { return isSystemRole; }
    public void setSystemRole(boolean systemRole) { isSystemRole = systemRole; }
    public Set<PermissionJpaEntity> getPermissions() { return permissions; }
    public void setPermissions(Set<PermissionJpaEntity> permissions) { this.permissions = permissions; }
}
