package com.accessflow.infrastructure.persistence.repository;

import com.accessflow.infrastructure.persistence.entity.OrganizationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for OrganizationJpaEntity.
 */
@Repository
public interface OrganizationJpaRepository extends JpaRepository<OrganizationJpaEntity, UUID> {

    Optional<OrganizationJpaEntity> findBySlug(String slug);

    List<OrganizationJpaEntity> findByIsActiveTrue();

    boolean existsBySlug(String slug);
}
