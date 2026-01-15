package com.accessflow.infrastructure.persistence.mapper;

import com.accessflow.domain.Membership;
import com.accessflow.infrastructure.persistence.entity.MembershipJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Membership domain entity and MembershipJpaEntity.
 * Note: This mapper does NOT map User, Organization, or Role entities.
 * Those relationships are managed by repositories using foreign keys.
 */
@Component
public class MembershipMapper {

    private final UserMapper userMapper;
    private final OrganizationMapper organizationMapper;
    private final RoleMapper roleMapper;

    public MembershipMapper(UserMapper userMapper, 
                           OrganizationMapper organizationMapper,
                           RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.organizationMapper = organizationMapper;
        this.roleMapper = roleMapper;
    }

    public MembershipJpaEntity toJpa(Membership domain) {
        if (domain == null) {
            return null;
        }

        MembershipJpaEntity jpa = new MembershipJpaEntity();
        jpa.setId(domain.getId());
        jpa.setActive(domain.isActive());
        jpa.setCreatedAt(domain.getCreatedAt());
        jpa.setUpdatedAt(domain.getUpdatedAt());

        // Map related entities
        jpa.setUser(userMapper.toJpa(domain.getUser()));
        jpa.setOrganization(organizationMapper.toJpa(domain.getOrganization()));
        jpa.setRole(roleMapper.toJpa(domain.getRole()));

        return jpa;
    }

    public Membership toDomain(MembershipJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        return new Membership(
            jpa.getId(),
            userMapper.toDomain(jpa.getUser()),
            organizationMapper.toDomain(jpa.getOrganization()),
            roleMapper.toDomain(jpa.getRole()),
            jpa.isActive(),
            jpa.getCreatedAt(),
            jpa.getUpdatedAt()
        );
    }

    public void updateJpaFromDomain(Membership domain, MembershipJpaEntity jpa) {
        if (domain == null || jpa == null) {
            return;
        }

        jpa.setActive(domain.isActive());
        jpa.setUpdatedAt(domain.getUpdatedAt());
        
        // Update role (user and organization are immutable for a membership)
        jpa.setRole(roleMapper.toJpa(domain.getRole()));
    }
}
