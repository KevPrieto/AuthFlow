package com.accessflow.infrastructure.persistence.repository;

import com.accessflow.infrastructure.persistence.entity.AuditLogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA repository for AuditLogJpaEntity.
 */
@Repository
public interface AuditLogJpaRepository extends JpaRepository<AuditLogJpaEntity, UUID> {

    List<AuditLogJpaEntity> findByActorId(UUID actorId);

    List<AuditLogJpaEntity> findByOrganizationId(UUID organizationId);

    List<AuditLogJpaEntity> findByResourceTypeAndResourceId(String resourceType, UUID resourceId);

    List<AuditLogJpaEntity> findByTimestampBetween(Instant start, Instant end);

    List<AuditLogJpaEntity> findByActorIdAndTimestampBetween(UUID actorId, Instant start, Instant end);
}
