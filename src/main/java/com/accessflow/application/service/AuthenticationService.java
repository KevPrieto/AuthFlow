package com.accessflow.application.service;

import com.accessflow.application.dto.AuthResponse;
import com.accessflow.application.dto.LoginRequest;
import com.accessflow.application.dto.RegisterUserRequest;
import com.accessflow.application.dto.UserDto;
import com.accessflow.application.mapper.UserDtoMapper;
import com.accessflow.domain.Session;
import com.accessflow.domain.User;
import com.accessflow.domain.repository.SessionRepository;
import com.accessflow.domain.repository.UserRepository;
import com.accessflow.domain.valueobjects.Email;
import com.accessflow.domain.valueobjects.PasswordHash;
import com.accessflow.infrastructure.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Application service for authentication operations.
 * Handles user registration and login use cases.
 */
@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDtoMapper userDtoMapper;

    public AuthenticationService(
            UserRepository userRepository,
            SessionRepository sessionRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDtoMapper = userDtoMapper;
    }

    /**
     * Registers a new user.
     */
    public AuthResponse register(RegisterUserRequest request) {
        // Check if user already exists
        Email email = new Email(request.getEmail());
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email " + request.getEmail() + " already exists");
        }

        // Hash password
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        PasswordHash passwordHash = new PasswordHash(hashedPassword);

        // Create user
        Instant now = Instant.now();
        User user = new User(UUID.randomUUID(), email, passwordHash, now);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.activate(now); // Activate immediately (no email verification for MVP)

        // Save user
        user = userRepository.save(user);

        // Generate JWT token
        String token = jwtService.generateToken(user.getId(), user.getEmail().getValue());
        Instant expiresAt = jwtService.getExpirationTime();

        // Create session
        Session session = new Session(UUID.randomUUID(), user, token, expiresAt, now);
        sessionRepository.save(session);

        // Return auth response
        UserDto userDto = userDtoMapper.toDto(user);
        return new AuthResponse(token, expiresAt, userDto);
    }

    /**
     * Authenticates a user and generates a JWT token.
     */
    public AuthResponse login(LoginRequest request) {
        // Find user by email
        Email email = new Email(request.getEmail());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash().getValue())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Check if user can authenticate
        if (!user.canAuthenticate()) {
            throw new IllegalStateException("User account is not active");
        }

        // Generate JWT token
        String token = jwtService.generateToken(user.getId(), user.getEmail().getValue());
        Instant expiresAt = jwtService.getExpirationTime();

        // Create session
        Instant now = Instant.now();
        Session session = new Session(UUID.randomUUID(), user, token, expiresAt, now);
        sessionRepository.save(session);

        // Return auth response
        UserDto userDto = userDtoMapper.toDto(user);
        return new AuthResponse(token, expiresAt, userDto);
    }

    /**
     * Logs out a user by revoking their session.
     */
    public void logout(String token) {
        sessionRepository.findByToken(token).ifPresent(session -> {
            session.revoke(Instant.now());
            sessionRepository.save(session);
        });
    }
}
