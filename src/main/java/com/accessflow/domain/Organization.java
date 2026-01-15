package com.accessflow.domain;

import java.time.Instant;
import java.util.*;

/**
 * Organization domain entity representing a multi-tenant organization.
 * Pure POJO with no framework dependencies - follows Clean Architecture principles.
 */
public class Organization {

    private UUID id;
    private String name;
    private OrganizationPlan plan;
    private String slug;
    private String description;
    private boolean isActive;
    private final Set<Membership> memberships;
    private Instant createdAt;
    private Instant updatedAt;

    /**
     * Creates a new organization with the given name.
     * Organization is created with FREE plan and active status by default.
     */
    public Organization(UUID id, String name, Instant now) {
        if (id == null) {
            throw new IllegalArgumentException("Organization ID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Organization name cannot be null or empty");
        }
        if (now == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        this.id = id;
        this.name = name.trim();
        this.plan = OrganizationPlan.FREE;
        this.isActive = true;
        this.memberships = new HashSet<>();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Reconstitutes an organization from persistence (used by repositories).
     */
    public Organization(UUID id, String name, OrganizationPlan plan, String slug,
                       String description, boolean isActive, Set<Membership> memberships,
                       Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.plan = plan;
        this.slug = slug;
        this.description = description;
        this.isActive = isActive;
        this.memberships = memberships != null ? new HashSet<>(memberships) : new HashSet<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(String newName, Instant now) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Organization name cannot be null or empty");
        }
        this.name = newName.trim();
        this.updatedAt = now;
    }

    public OrganizationPlan getPlan() {
        return plan;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
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
     * Upgrades the organization to a new plan.
     * Uses explicit tier comparison instead of fragile ordinal().
     */
    public void upgradePlan(OrganizationPlan newPlan, Instant now) {
        if (newPlan == null) {
            throw new IllegalArgumentException("New plan cannot be null");
        }
        if (!newPlan.isHigherThan(this.plan)) {
            throw new IllegalArgumentException(
                "Can only upgrade to a higher plan. Current: " + this.plan + ", Requested: " + newPlan
            );
        }
        this.plan = newPlan;
        this.updatedAt = now;
    }

    /**
     * Downgrades the organization to a new plan.
     */
    public void downgradePlan(OrganizationPlan newPlan, Instant now) {
        if (newPlan == null) {
            throw new IllegalArgumentException("New plan cannot be null");
        }
        if (!newPlan.isLowerThan(this.plan)) {
            throw new IllegalArgumentException(
                "Can only downgrade to a lower plan. Current: " + this.plan + ", Requested: " + newPlan
            );
        }
        this.plan = newPlan;
        this.updatedAt = now;
    }

    /**
     * Deactivates the organization.
     */
    public void deactivate(Instant now) {
        this.isActive = false;
        this.updatedAt = now;
    }

    /**
     * Reactivates the organization.
     */
    public void reactivate(Instant now) {
        this.isActive = true;
        this.updatedAt = now;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
