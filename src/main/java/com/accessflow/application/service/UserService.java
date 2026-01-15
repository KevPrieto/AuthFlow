package com.accessflow.application.service;

import com.accessflow.application.dto.UserDto;
import com.accessflow.application.mapper.UserDtoMapper;
import com.accessflow.domain.User;
import com.accessflow.domain.repository.UserRepository;
import com.accessflow.domain.valueobjects.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Application service for user operations.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    /**
     * Gets user by ID.
     */
    public UserDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return userDtoMapper.toDto(user);
    }

    /**
     * Gets user by email.
     */
    public UserDto getUserByEmail(String emailString) {
        Email email = new Email(emailString);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + emailString));
        return userDtoMapper.toDto(user);
    }

    /**
     * Gets current user's profile.
     */
    public UserDto getCurrentUserProfile(UUID userId) {
        return getUserById(userId);
    }
}
