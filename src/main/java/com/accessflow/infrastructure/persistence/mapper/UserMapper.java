package com.accessflow.infrastructure.persistence.mapper;

import com.accessflow.domain.User;
import com.accessflow.domain.UserStatus;
import com.accessflow.domain.valueobjects.Email;
import com.accessflow.domain.valueobjects.PasswordHash;
import com.accessflow.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Mapper for converting between User domain entity and UserJpaEntity.
 * Part of infrastructure layer - bridges domain and persistence.
 */
@Component
public class UserMapper {

    /**
     * Converts domain User to JPA entity for persistence.
     * Note: Does NOT map memberships to avoid circular dependencies.
     * Memberships should be handled separately by MembershipMapper.
     */
    public UserJpaEntity toJpa(User domain) {
        if (domain == null) {
            return null;
        }

        UserJpaEntity jpa = new UserJpaEntity();
        jpa.setId(domain.getId());
        jpa.setEmail(domain.getEmail().getValue());
        jpa.setPasswordHash(domain.getPasswordHash().getValue());
        jpa.setStatus(domain.getStatus());
        jpa.setFirstName(domain.getFirstName());
        jpa.setLastName(domain.getLastName());
        jpa.setCreatedAt(domain.getCreatedAt());
        jpa.setUpdatedAt(domain.getUpdatedAt());
        // Memberships NOT mapped here - handled separately

        return jpa;
    }

    /**
     * Converts JPA entity to domain User.
     * Note: Returns User without memberships - they should be loaded separately if needed.
     */
    public User toDomain(UserJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return new User(
            jpa.getId(),
            new Email(jpa.getEmail()),
            new PasswordHash(jpa.getPasswordHash()),
            jpa.getStatus(),
            jpa.getFirstName(),
            jpa.getLastName(),
            new HashSet<>(), // Memberships loaded separately
            jpa.getCreatedAt(),
            jpa.getUpdatedAt()
        );
    }

    /**
     * Updates an existing JPA entity with data from domain entity.
     * Useful for update operations where the JPA entity is already managed.
     */
    public void updateJpaFromDomain(User domain, UserJpaEntity jpa) {
        if (domain == null || jpa == null) {
            return;
        }

        jpa.setEmail(domain.getEmail().getValue());
        jpa.setPasswordHash(domain.getPasswordHash().getValue());
        jpa.setStatus(domain.getStatus());
        jpa.setFirstName(domain.getFirstName());
        jpa.setLastName(domain.getLastName());
        jpa.setUpdatedAt(domain.getUpdatedAt());
        // ID and createdAt are immutable, don't update
    }
}
