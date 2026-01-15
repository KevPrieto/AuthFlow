package com.accessflow.infrastructure.persistence.repository;

import com.accessflow.domain.Session;
import com.accessflow.domain.repository.SessionRepository;
import com.accessflow.infrastructure.persistence.mapper.SessionMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SessionRepositoryImpl implements SessionRepository {

    private final SessionJpaRepository jpaRepository;
    private final SessionMapper mapper;

    public SessionRepositoryImpl(SessionJpaRepository jpaRepository, SessionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Session save(Session session) {
        var jpaEntity = mapper.toJpa(session);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Session> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }

    @Override
    public Optional<Session> findByToken(String token) {
        return jpaRepository.findByToken(token)
            .map(mapper::toDomain);
    }

    @Override
    public List<Session> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Session> findValidByUserId(UUID userId, Instant now) {
        return jpaRepository.findValidByUserId(userId, now).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByUserId(UUID userId) {
        jpaRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteExpiredSessions(Instant now) {
        jpaRepository.deleteExpiredSessions(now);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
