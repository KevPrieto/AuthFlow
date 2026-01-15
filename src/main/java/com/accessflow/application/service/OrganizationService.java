package com.accessflow.application.service;

import com.accessflow.application.dto.CreateOrganizationRequest;
import com.accessflow.application.dto.OrganizationDto;
import com.accessflow.application.mapper.OrganizationDtoMapper;
import com.accessflow.domain.Organization;
import com.accessflow.domain.repository.OrganizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Application service for organization operations.
 */
@Service
@Transactional
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationDtoMapper organizationDtoMapper;

    public OrganizationService(
            OrganizationRepository organizationRepository,
            OrganizationDtoMapper organizationDtoMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationDtoMapper = organizationDtoMapper;
    }

    /**
     * Creates a new organization.
     */
    public OrganizationDto createOrganization(CreateOrganizationRequest request) {
        // Check if slug already exists
        if (request.getSlug() != null && organizationRepository.existsBySlug(request.getSlug())) {
            throw new IllegalArgumentException("Organization with slug " + request.getSlug() + " already exists");
        }

        // Create organization
        Instant now = Instant.now();
        Organization organization = new Organization(UUID.randomUUID(), request.getName(), now);
        organization.setSlug(request.getSlug());
        organization.setDescription(request.getDescription());

        // Save organization
        organization = organizationRepository.save(organization);

        return organizationDtoMapper.toDto(organization);
    }

    /**
     * Gets organization by ID.
     */
    @Transactional(readOnly = true)
    public OrganizationDto getOrganizationById(UUID id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Organization not found with id: " + id));
        return organizationDtoMapper.toDto(organization);
    }

    /**
     * Gets all active organizations.
     */
    @Transactional(readOnly = true)
    public List<OrganizationDto> getAllActiveOrganizations() {
        return organizationRepository.findAllActive().stream()
                .map(organizationDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
