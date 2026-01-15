package com.accessflow.application.mapper;

import com.accessflow.application.dto.UserDto;
import com.accessflow.domain.User;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between User domain entity and UserDto.
 */
@Component
public class UserDtoMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail().getValue());
        dto.setStatus(user.getStatus().name());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }
}
