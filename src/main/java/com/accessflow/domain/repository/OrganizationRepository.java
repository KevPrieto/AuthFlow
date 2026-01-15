package com.accessflow.domain.repository;

import com.accessflow.domain.Organization;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Organization aggregate root.
 */
public interface OrganizationRepository {

    Organization save(Organization organization);

    Optional<Organization> findById(UUID id);

    Optional<Organization> findBySlug(String slug);

    List<Organization> findAllActive();

    boolean existsBySlug(String slug);

    void deleteById(UUID id);

    long count();
}
