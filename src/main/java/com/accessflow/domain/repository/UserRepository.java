package com.accessflow.domain.repository;

import com.accessflow.domain.User;
import com.accessflow.domain.valueobjects.Email;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for User aggregate root.
 * This is a domain interface - framework-independent.
 * Implementations are in the infrastructure layer.
 */
public interface UserRepository {

    /**
     * Saves a user (create or update).
     */
    User save(User user);

    /**
     * Finds a user by ID.
     */
    Optional<User> findById(UUID id);

    /**
     * Finds a user by email.
     */
    Optional<User> findByEmail(Email email);

    /**
     * Checks if a user exists with the given email.
     */
    boolean existsByEmail(Email email);

    /**
     * Deletes a user by ID.
     */
    void deleteById(UUID id);

    /**
     * Counts all users.
     */
    long count();
}
