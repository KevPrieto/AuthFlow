package com.accessflow.domain.repository;

import com.accessflow.domain.Membership;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Membership aggregate root.
 */
public interface MembershipRepository {

    Membership save(Membership membership);

    Optional<Membership> findById(UUID id);

    Optional<Membership> findByUserIdAndOrganizationId(UUID userId, UUID organizationId);

    List<Membership> findByUserId(UUID userId);

    List<Membership> findByOrganizationId(UUID organizationId);

    List<Membership> findActiveByUserId(UUID userId);

    List<Membership> findActiveByOrganizationId(UUID organizationId);

    boolean existsByUserIdAndOrganizationId(UUID userId, UUID organizationId);

    void deleteById(UUID id);

    long count();
}
