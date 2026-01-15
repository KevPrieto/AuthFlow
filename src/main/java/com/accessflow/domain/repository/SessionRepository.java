package com.accessflow.domain.repository;

import com.accessflow.domain.Session;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for Session aggregate root.
 */
public interface SessionRepository {

    Session save(Session session);

    Optional<Session> findById(UUID id);

    Optional<Session> findByToken(String token);

    List<Session> findByUserId(UUID userId);

    List<Session> findValidByUserId(UUID userId, Instant now);

    void deleteById(UUID id);

    void deleteByUserId(UUID userId);

    void deleteExpiredSessions(Instant now);

    long count();
}
