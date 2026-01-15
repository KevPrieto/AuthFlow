package com.accessflow.infrastructure.persistence.mapper;

import com.accessflow.domain.Session;
import com.accessflow.infrastructure.persistence.entity.SessionJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Session domain entity and SessionJpaEntity.
 */
@Component
public class SessionMapper {

    private final UserMapper userMapper;

    public SessionMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public SessionJpaEntity toJpa(Session domain) {
        if (domain == null) {
            return null;
        }

        SessionJpaEntity jpa = new SessionJpaEntity();
        jpa.setId(domain.getId());
        jpa.setToken(domain.getToken());
        jpa.setExpiresAt(domain.getExpiresAt());
        jpa.setRevoked(domain.isRevoked());
        jpa.setIpAddress(domain.getIpAddress());
        jpa.setUserAgent(domain.getUserAgent());
        jpa.setCreatedAt(domain.getCreatedAt());
        jpa.setUpdatedAt(domain.getUpdatedAt());

        // Map user
        jpa.setUser(userMapper.toJpa(domain.getUser()));

        return jpa;
    }

    public Session toDomain(SessionJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return new Session(
            jpa.getId(),
            jpa.getToken(),
            userMapper.toDomain(jpa.getUser()),
            jpa.getExpiresAt(),
            jpa.isRevoked(),
            jpa.getIpAddress(),
            jpa.getUserAgent(),
            jpa.getCreatedAt(),
            jpa.getUpdatedAt()
        );
    }

    public void updateJpaFromDomain(Session domain, SessionJpaEntity jpa) {
        if (domain == null || jpa == null) {
            return;
        }

        jpa.setExpiresAt(domain.getExpiresAt());
        jpa.setRevoked(domain.isRevoked());
        jpa.setIpAddress(domain.getIpAddress());
        jpa.setUserAgent(domain.getUserAgent());
        jpa.setUpdatedAt(domain.getUpdatedAt());
        // Token, user, and createdAt are immutable
    }
}
