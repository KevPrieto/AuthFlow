package com.accessflow.application.mapper;

import com.accessflow.application.dto.PermissionDto;
import com.accessflow.domain.Permission;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Permission domain entity and PermissionDto.
 */
@Component
public class PermissionDtoMapper {

    public PermissionDto toDto(Permission permission) {
        if (permission == null) {
            return null;
        }

        PermissionDto dto = new PermissionDto();
        dto.setId(permission.getId());
        dto.setKey(permission.getKey());
        dto.setDescription(permission.getDescription());
        dto.setResource(permission.getResource());
        dto.setAction(permission.getAction());

        return dto;
    }
}
