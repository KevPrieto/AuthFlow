package com.accessflow.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions", indexes = {
    @Index(name = "idx_permission_key", columnList = "permission_key", unique = true),
    @Index(name = "idx_permission_resource", columnList = "resource")
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

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getResource() { return resource; }
    public void setResource(String resource) { this.resource = resource; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}
