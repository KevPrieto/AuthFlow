package com.accessflow.api.controller;

import com.accessflow.application.dto.UserDto;
import com.accessflow.application.service.UserService;
import com.accessflow.infrastructure.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST API controller for user-related endpoints.
 * Handles user profile and permission queries.
 */
@RestController
@RequestMapping("/me")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * GET /me
     * Returns the current authenticated user's profile.
     */
    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        UUID userId = extractUserIdFromToken(authorizationHeader);
        UserDto user = userService.getCurrentUserProfile(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * GET /me/permissions
     * Returns the current user's effective permissions.
     * TODO: Implement permission resolution in future iteration.
     */
    @GetMapping("/permissions")
    public ResponseEntity<String> getCurrentUserPermissions(@RequestHeader("Authorization") String authorizationHeader) {
        // Placeholder for permission resolution engine
        return ResponseEntity.ok("Permission resolution not yet implemented");
    }

    /**
     * Extracts user ID from JWT token in Authorization header.
     */
    private UUID extractUserIdFromToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return jwtService.extractUserId(token);
        }
        throw new IllegalArgumentException("Invalid Authorization header format");
    }
}
