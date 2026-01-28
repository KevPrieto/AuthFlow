package com.accessflow.infrastructure.persistence.entity;

import com.accessflow.domain.UserStatus;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity representing an authenticated user in the system.
 * Users can be members of multiple organizations with different roles.
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_email", columnList = "email", unique = true)
})
public class UserJpaEntity extends BaseJpaEntity {

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private UserStatus status;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MembershipJpaEntity> memberships = new HashSet<>();

    public UserJpaEntity() {
    }

    /**
     * Creates a new user with the given email and password hash.
     * User is created in PENDING status by default.
     */
    public UserJpaEntity(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = UserStatus.PENDING;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
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

    public Set<MembershipJpaEntity> getMemberships() {
        return memberships;
    }

    /**
     * Activates the user account, allowing authentication.
     */
    public void activate() {
        this.status = UserStatus.ACTIVE;
    }

    /**
     * Suspends the user account, preventing authentication.
     */
    public void suspend() {
        this.status = UserStatus.SUSPENDED;
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
}
