package com.accessflow.infrastructure.persistence.repository;

import com.accessflow.infrastructure.persistence.entity.PermissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for PermissionJpaEntity.
 */
@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionJpaEntity, UUID> {

    Optional<PermissionJpaEntity> findByKey(String key);

    List<PermissionJpaEntity> findByResource(String resource);

    boolean existsByKey(String key);
}
