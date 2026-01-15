package com.accessflow.infrastructure.persistence.repository;

import com.accessflow.infrastructure.persistence.entity.SessionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository for SessionJpaEntity.
 */
@Repository
public interface SessionJpaRepository extends JpaRepository<SessionJpaEntity, UUID> {

    Optional<SessionJpaEntity> findByToken(String token);

    @Query("SELECT s FROM SessionJpaEntity s WHERE s.user.id = :userId")
    List<SessionJpaEntity> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT s FROM SessionJpaEntity s WHERE s.user.id = :userId AND s.isRevoked = false AND s.expiresAt > :now")
    List<SessionJpaEntity> findValidByUserId(@Param("userId") UUID userId, @Param("now") Instant now);

    @Modifying
    @Query("DELETE FROM SessionJpaEntity s WHERE s.user.id = :userId")
    void deleteByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query("DELETE FROM SessionJpaEntity s WHERE s.expiresAt < :now")
    void deleteExpiredSessions(@Param("now") Instant now);
}
