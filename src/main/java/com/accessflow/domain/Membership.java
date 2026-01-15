package com.accessflow.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Membership domain entity representing the relationship between a User and an Organization.
 * Each membership assigns a Role to the user within the organization context.
 * Pure POJO with no framework dependencies - follows Clean Architecture principles.
 */
public class Membership {

    private UUID id;
    private User user;
    private Organization organization;
    private Role role;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Creates a new membership connecting a user to an organization with a specific role.
     */
    public Membership(UUID id, User user, Organization organization, Role role, Instant now) {
        if (id == null) {
            throw new IllegalArgumentException("Membership ID cannot be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (organization == null) {
            throw new IllegalArgumentException("Organization cannot be null");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        if (now == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        this.id = id;
        this.user = user;
        this.organization = organization;
        this.role = role;
        this.isActive = true;
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Reconstitutes a membership from persistence (used by repositories).
     */
    public Membership(UUID id, User user, Organization organization, Role role,
                     boolean isActive, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.user = user;
        this.organization = organization;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public Role getRole() {
        return role;
    }

    public void changeRole(Role newRole, Instant now) {
        if (newRole == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.role = newRole;
        this.updatedAt = now;
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Activates this membership.
     */
    public void activate(Instant now) {
        this.isActive = true;
        this.updatedAt = now;
    }

    /**
     * Deactivates this membership.
     */
    public void deactivate(Instant now) {
        this.isActive = false;
        this.updatedAt = now;
    }

    /**
     * Checks if the user has a specific permission through this membership.
     */
    public boolean hasPermission(String permissionKey) {
        return isActive && role.hasPermission(permissionKey);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Membership that = (Membership) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
