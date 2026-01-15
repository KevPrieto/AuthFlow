package com.accessflow.application.dto;

import java.util.UUID;

/**
 * DTO for Permission entity.
 */
public class PermissionDto {

    private UUID id;
    private String key;
    private String description;
    private String resource;
    private String action;

    public PermissionDto() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getResource() { return resource; }
    public void setResource(String resource) { this.resource = resource; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}
