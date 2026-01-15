package com.accessflow.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new organization.
 */
public class CreateOrganizationRequest {

    @NotBlank(message = "Organization name is required")
    @Size(min = 2, max = 255, message = "Organization name must be between 2 and 255 characters")
    private String name;

    @Size(max = 100, message = "Slug cannot exceed 100 characters")
    private String slug;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    public CreateOrganizationRequest() {
    }

    public CreateOrganizationRequest(String name, String slug, String description) {
        this.name = name;
        this.slug = slug;
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
