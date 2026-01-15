package com.accessflow.domain;

import com.accessflow.domain.valueobjects.Email;
import com.accessflow.domain.valueobjects.PasswordHash;

import java.time.Instant;
import java.util.*;

/**
 * User domain entity representing an authenticated user in the system.
 * Pure POJO with no framework dependencies - follows Clean Architecture principles.
 */
public class User {

    private UUID id;
    private Email email;
    private PasswordHash passwordHash;
    private UserStatus status;
    private String firstName;
    private String lastName;
    private final Set<Membership> memberships;
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Creates a new user with the given email and password hash.
     * User is created in PENDING status by default.
     */
    public User(UUID id, Email email, PasswordHash passwordHash, Instant now) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (passwordHash == null) {
            throw new IllegalArgumentException("Password hash cannot be null");
        }
        if (now == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = UserStatus.PENDING;
        this.memberships = new HashSet<>();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Reconstitutes a user from persistence (used by repositories).
     */
    public User(UUID id, Email email, PasswordHash passwordHash, UserStatus status,
                String firstName, String lastName, Set<Membership> memberships,
                Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberships = memberships != null ? new HashSet<>(memberships) : new HashSet<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public void changeEmail(Email newEmail, Instant now) {
        if (newEmail == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        this.email = newEmail;
        this.updatedAt = now;
    }

    public PasswordHash getPasswordHash() {
        return passwordHash;
    }

    public void changePassword(PasswordHash newPasswordHash, Instant now) {
        if (newPasswordHash == null) {
            throw new IllegalArgumentException("Password hash cannot be null");
        }
        this.passwordHash = newPasswordHash;
        this.updatedAt = now;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns an unmodifiable view of memberships.
     * Prevents external modification of internal state.
     */
    public Set<Membership> getMemberships() {
        return Collections.unmodifiableSet(memberships);
    }

    public void addMembership(Membership membership) {
        if (membership == null) {
            throw new IllegalArgumentException("Membership cannot be null");
        }
        this.memberships.add(membership);
    }

    public void removeMembership(Membership membership) {
        this.memberships.remove(membership);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Activates the user account, allowing authentication.
     */
    public void activate(Instant now) {
        this.status = UserStatus.ACTIVE;
        this.updatedAt = now;
    }

    /**
     * Suspends the user account, preventing authentication.
     */
    public void suspend(Instant now) {
        this.status = UserStatus.SUSPENDED;
        this.updatedAt = now;
    }

    /**
     * Soft deletes the user account.
     */
    public void delete(Instant now) {
        this.status = UserStatus.DELETED;
        this.updatedAt = now;
    }

    /**
     * Checks if the user account is active.
     */
    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    /**
     * Checks if the user can authenticate.
     */
    public boolean canAuthenticate() {
        return this.status == UserStatus.ACTIVE || this.status == UserStatus.PENDING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
