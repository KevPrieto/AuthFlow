package com.accessflow.api.controller;

import com.accessflow.application.dto.CreateOrganizationRequest;
import com.accessflow.application.dto.OrganizationDto;
import com.accessflow.application.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST API controller for organization management.
 * Handles organization creation, retrieval, and management.
 */
@RestController
@RequestMapping("/orgs")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * POST /orgs
     * Creates a new organization.
     */
    @PostMapping
    public ResponseEntity<OrganizationDto> createOrganization(@Valid @RequestBody CreateOrganizationRequest request) {
        OrganizationDto organization = organizationService.createOrganization(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(organization);
    }

    /**
     * GET /orgs/{id}
     * Retrieves an organization by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> getOrganization(@PathVariable UUID id) {
        OrganizationDto organization = organizationService.getOrganizationById(id);
        return ResponseEntity.ok(organization);
    }

    /**
     * GET /orgs
     * Lists all active organizations.
     */
    @GetMapping
    public ResponseEntity<List<OrganizationDto>> listOrganizations() {
        List<OrganizationDto> organizations = organizationService.getAllActiveOrganizations();
        return ResponseEntity.ok(organizations);
    }

    /**
     * POST /orgs/{id}/invite
     * Invites a user to an organization.
     * TODO: Implement in future iteration.
     */
    @PostMapping("/{id}/invite")
    public ResponseEntity<String> inviteUser(@PathVariable UUID id) {
        return ResponseEntity.ok("Organization invite not yet implemented");
    }
}
