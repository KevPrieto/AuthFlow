package com.accessflow.application.mapper;

import com.accessflow.application.dto.OrganizationDto;
import com.accessflow.domain.Organization;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Organization domain entity and OrganizationDto.
 */
@Component
public class OrganizationDtoMapper {

    public OrganizationDto toDto(Organization organization) {
        if (organization == null) {
            return null;
        }

        OrganizationDto dto = new OrganizationDto();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setPlan(organization.getPlan().name());
        dto.setSlug(organization.getSlug());
        dto.setDescription(organization.getDescription());
        dto.setActive(organization.isActive());
        dto.setCreatedAt(organization.getCreatedAt());
        dto.setUpdatedAt(organization.getUpdatedAt());

        return dto;
    }
}
