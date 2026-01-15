package com.accessflow.domain.repository;

import com.accessflow.domain.AuditLog;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for AuditLog aggregate root.
 */
public interface AuditLogRepository {

    AuditLog save(AuditLog auditLog);

    Optional<AuditLog> findById(UUID id);

    List<AuditLog> findByActorId(UUID actorId);

    List<AuditLog> findByOrganizationId(UUID organizationId);

    List<AuditLog> findByResourceTypeAndResourceId(String resourceType, UUID resourceId);

    List<AuditLog> findByTimestampBetween(Instant start, Instant end);

    List<AuditLog> findByActorIdAndTimestampBetween(UUID actorId, Instant start, Instant end);

    long count();
}
