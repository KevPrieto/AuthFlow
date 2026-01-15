package com.accessflow.infrastructure.persistence.mapper;

import com.accessflow.domain.Organization;
import com.accessflow.infrastructure.persistence.entity.OrganizationJpaEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Mapper for converting between Organization domain entity and OrganizationJpaEntity.
 */
@Component
public class OrganizationMapper {

    public OrganizationJpaEntity toJpa(Organization domain) {
        if (domain == null) {
            return null;
        }

        OrganizationJpaEntity jpa = new OrganizationJpaEntity();
        jpa.setId(domain.getId());
        jpa.setName(domain.getName());
        jpa.setPlan(domain.getPlan());
        jpa.setSlug(domain.getSlug());
        jpa.setDescription(domain.getDescription());
        jpa.setActive(domain.isActive());
        jpa.setCreatedAt(domain.getCreatedAt());
        jpa.setUpdatedAt(domain.getUpdatedAt());
        // Memberships NOT mapped here - handled separately

        return jpa;
    }

    public Organization toDomain(OrganizationJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return new Organization(
            jpa.getId(),
            jpa.getName(),
            jpa.getPlan(),
            jpa.getSlug(),
            jpa.getDescription(),
            jpa.isActive(),
            new HashSet<>(), // Memberships loaded separately
            jpa.getCreatedAt(),
            jpa.getUpdatedAt()
        );
    }

    public void updateJpaFromDomain(Organization domain, OrganizationJpaEntity jpa) {
        if (domain == null || jpa == null) {
            return;
        }

        jpa.setName(domain.getName());
        jpa.setPlan(domain.getPlan());
        jpa.setSlug(domain.getSlug());
        jpa.setDescription(domain.getDescription());
        jpa.setActive(domain.isActive());
        jpa.setUpdatedAt(domain.getUpdatedAt());
    }
}
