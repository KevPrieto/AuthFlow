package com.accessflow.application.mapper;

import com.accessflow.application.dto.RoleDto;
import com.accessflow.domain.Role;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper for converting between Role domain entity and RoleDto.
 */
@Component
public class RoleDtoMapper {

    private final PermissionDtoMapper permissionMapper;

    public RoleDtoMapper(PermissionDtoMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public RoleDto toDto(Role role) {
        if (role == null) {
            return null;
        }

        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        dto.setSystemRole(role.isSystemRole());
        dto.setPermissions(
            role.getPermissions().stream()
                .map(permissionMapper::toDto)
                .collect(Collectors.toSet())
        );
        dto.setCreatedAt(role.getCreatedAt());
        dto.setUpdatedAt(role.getUpdatedAt());

        return dto;
    }
}
