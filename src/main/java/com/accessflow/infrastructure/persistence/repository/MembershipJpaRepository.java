package com.accessflow.infrastructure.persistence.repository;

import com.accessflow.infrastructure.persistence.entity.MembershipJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for MembershipJpaEntity.
 */
@Repository
public interface MembershipJpaRepository extends JpaRepository<MembershipJpaEntity, UUID> {

    @Query("SELECT m FROM MembershipJpaEntity m WHERE m.user.id = :userId AND m.organization.id = :organizationId")
    Optional<MembershipJpaEntity> findByUserIdAndOrganizationId(@Param("userId") UUID userId, 
                                                                  @Param("organizationId") UUID organizationId);

    @Query("SELECT m FROM MembershipJpaEntity m WHERE m.user.id = :userId")
    List<MembershipJpaEntity> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT m FROM MembershipJpaEntity m WHERE m.organization.id = :organizationId")
    List<MembershipJpaEntity> findByOrganizationId(@Param("organizationId") UUID organizationId);

    @Query("SELECT m FROM MembershipJpaEntity m WHERE m.user.id = :userId AND m.isActive = true")
    List<MembershipJpaEntity> findActiveByUserId(@Param("userId") UUID userId);

    @Query("SELECT m FROM MembershipJpaEntity m WHERE m.organization.id = :organizationId AND m.isActive = true")
    List<MembershipJpaEntity> findActiveByOrganizationId(@Param("organizationId") UUID organizationId);

    @Query("SELECT COUNT(m) > 0 FROM MembershipJpaEntity m WHERE m.user.id = :userId AND m.organization.id = :organizationId")
    boolean existsByUserIdAndOrganizationId(@Param("userId") UUID userId, @Param("organizationId") UUID organizationId);
}
