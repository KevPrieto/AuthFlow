package com.accessflow.infrastructure.persistence.repository;

import com.accessflow.domain.AuditLog;
import com.accessflow.domain.repository.AuditLogRepository;
import com.accessflow.infrastructure.persistence.mapper.AuditLogMapper;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AuditLogRepositoryImpl implements AuditLogRepository {

    private final AuditLogJpaRepository jpaRepository;
    private final AuditLogMapper mapper;

    public AuditLogRepositoryImpl(AuditLogJpaRepository jpaRepository, AuditLogMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public AuditLog save(AuditLog auditLog) {
        var jpaEntity = mapper.toJpa(auditLog);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<AuditLog> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }

    @Override
    public List<AuditLog> findByActorId(UUID actorId) {
        return jpaRepository.findByActorId(actorId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findByOrganizationId(UUID organizationId) {
        return jpaRepository.findByOrganizationId(organizationId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findByResourceTypeAndResourceId(String resourceType, UUID resourceId) {
        return jpaRepository.findByResourceTypeAndResourceId(resourceType, resourceId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findByTimestampBetween(Instant start, Instant end) {
        return jpaRepository.findByTimestampBetween(start, end).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> findByActorIdAndTimestampBetween(UUID actorId, Instant start, Instant end) {
        return jpaRepository.findByActorIdAndTimestampBetween(actorId, start, end).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
