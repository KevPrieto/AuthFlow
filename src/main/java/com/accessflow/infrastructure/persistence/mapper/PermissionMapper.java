package com.accessflow.infrastructure.persistence.mapper;

import com.accessflow.domain.Permission;
import com.accessflow.infrastructure.persistence.entity.PermissionJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Permission domain entity and PermissionJpaEntity.
 */
@Component
public class PermissionMapper {

    public PermissionJpaEntity toJpa(Permission domain) {
        if (domain == null) {
            return null;
        }

        PermissionJpaEntity jpa = new PermissionJpaEntity();
        jpa.setId(domain.getId());
        jpa.setKey(domain.getKey());
        jpa.setDescription(domain.getDescription());
        jpa.setResource(domain.getResource());
        jpa.setAction(domain.getAction());
        jpa.setCreatedAt(domain.getCreatedAt());
        jpa.setUpdatedAt(domain.getUpdatedAt());

        return jpa;
    }

    public Permission toDomain(PermissionJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return new Permission(
            jpa.getId(),
            jpa.getKey(),
            jpa.getDescription(),
            jpa.getResource(),
            jpa.getAction(),
            jpa.getCreatedAt(),
            jpa.getUpdatedAt()
        );
    }

    public void updateJpaFromDomain(Permission domain, PermissionJpaEntity jpa) {
        if (domain == null || jpa == null) {
            return;
        }

        jpa.setKey(domain.getKey());
        jpa.setDescription(domain.getDescription());
        jpa.setResource(domain.getResource());
        jpa.setAction(domain.getAction());
        jpa.setUpdatedAt(domain.getUpdatedAt());
    }
}
