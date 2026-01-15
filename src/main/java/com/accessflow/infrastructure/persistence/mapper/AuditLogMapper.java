package com.accessflow.infrastructure.persistence.mapper;

import com.accessflow.domain.AuditLog;
import com.accessflow.infrastructure.persistence.entity.AuditLogJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between AuditLog domain entity and AuditLogJpaEntity.
 */
@Component
public class AuditLogMapper {

    public AuditLogJpaEntity toJpa(AuditLog domain) {
        if (domain == null) {
            return null;
        }

        AuditLogJpaEntity jpa = new AuditLogJpaEntity();
        jpa.setId(domain.getId());
        jpa.setActorId(domain.getActorId());
        jpa.setActorEmail(domain.getActorEmail());
        jpa.setAction(domain.getAction());
        jpa.setResourceType(domain.getResourceType());
        jpa.setResourceId(domain.getResourceId());
        jpa.setOrganizationId(domain.getOrganizationId());
        jpa.setTimestamp(domain.getTimestamp());
        jpa.setIpAddress(domain.getIpAddress());
        jpa.setUserAgent(domain.getUserAgent());
        jpa.setDetails(domain.getDetails());
        jpa.setStatus(domain.getStatus());

        return jpa;
    }

    public AuditLog toDomain(AuditLogJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return new AuditLog(
            jpa.getId(),
            jpa.getActorId(),
            jpa.getActorEmail(),
            jpa.getAction(),
            jpa.getResourceType(),
            jpa.getResourceId(),
            jpa.getOrganizationId(),
            jpa.getTimestamp(),
            jpa.getIpAddress(),
            jpa.getUserAgent(),
            jpa.getDetails(),
            jpa.getStatus()
        );
    }
}
