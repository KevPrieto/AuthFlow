package com.accessflow.infrastructure.persistence.mapper;

import com.accessflow.domain.Permission;
import com.accessflow.domain.Role;
import com.accessflow.infrastructure.persistence.entity.PermissionJpaEntity;
import com.accessflow.infrastructure.persistence.entity.RoleJpaEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Role domain entity and RoleJpaEntity.
 */
@Component
public class RoleMapper {

    private final PermissionMapper permissionMapper;

    public RoleMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public RoleJpaEntity toJpa(Role domain) {
        if (domain == null) {
            return null;
        }

        RoleJpaEntity jpa = new RoleJpaEntity();
        jpa.setId(domain.getId());
        jpa.setName(domain.getName());
        jpa.setDescription(domain.getDescription());
        jpa.setSystemRole(domain.isSystemRole());
        jpa.setCreatedAt(domain.getCreatedAt());
        jpa.setUpdatedAt(domain.getUpdatedAt());

        // Map permissions
        Set<PermissionJpaEntity> permissionJpaEntities = domain.getPermissions().stream()
            .map(permissionMapper::toJpa)
            .collect(Collectors.toSet());
        jpa.setPermissions(permissionJpaEntities);

        return jpa;
    }

    public Role toDomain(RoleJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        // Map permissions
        Set<Permission> permissions = jpa.getPermissions().stream()
            .map(permissionMapper::toDomain)
            .collect(Collectors.toSet());

        return new Role(
            jpa.getId(),
            jpa.getName(),
            jpa.getDescription(),
            jpa.isSystemRole(),
            permissions,
            jpa.getCreatedAt(),
            jpa.getUpdatedAt()
        );
    }

    public void updateJpaFromDomain(Role domain, RoleJpaEntity jpa) {
        if (domain == null || jpa == null) {
            return;
        }

        jpa.setName(domain.getName());
        jpa.setDescription(domain.getDescription());
        jpa.setUpdatedAt(domain.getUpdatedAt());

        // Update permissions collection
        jpa.getPermissions().clear();
        Set<PermissionJpaEntity> permissionJpaEntities = domain.getPermissions().stream()
            .map(permissionMapper::toJpa)
            .collect(Collectors.toSet());
        jpa.getPermissions().addAll(permissionJpaEntities);
    }
}
