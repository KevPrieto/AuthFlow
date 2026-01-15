package com.accessflow.application.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for Role entity.
 */
public class RoleDto {

    private UUID id;
    private String name;
    private String description;
    private boolean isSystemRole;
    private Set<PermissionDto> permissions;
    private Instant createdAt;
    private Instant updatedAt;

    public RoleDto() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isSystemRole() { return isSystemRole; }
    public void setSystemRole(boolean systemRole) { isSystemRole = systemRole; }
    public Set<PermissionDto> getPermissions() { return permissions; }
    public void setPermissions(Set<PermissionDto> permissions) { this.permissions = permissions; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
