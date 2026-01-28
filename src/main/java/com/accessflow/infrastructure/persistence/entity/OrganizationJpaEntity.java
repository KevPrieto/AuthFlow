package com.accessflow.infrastructure.persistence.entity;

import com.accessflow.domain.OrganizationPlan;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Organization entity representing a multi-tenant organization.
 * Organizations can have multiple members with different roles.
 */
@Entity
@Table(name = "organizations", indexes = {
    @Index(name = "idx_org_name", columnList = "name")
})
public class OrganizationJpaEntity extends BaseJpaEntity {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan", nullable = false, length = 20)
    private OrganizationPlan plan;

    @Column(name = "slug", unique = true, length = 100)
    private String slug;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MembershipJpaEntity> memberships = new HashSet<>();

    public OrganizationJpaEntity() {
    }

    /**
     * Creates a new organization with the given name.
     * Organization is created with FREE plan and active status by default.
     */
    public OrganizationJpaEntity(String name) {
        this.name = name;
        this.plan = OrganizationPlan.FREE;
        this.isActive = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrganizationPlan getPlan() {
        return plan;
    }

    public void setPlan(OrganizationPlan plan) {
        this.plan = plan;
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

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<MembershipJpaEntity> getMemberships() {
        return memberships;
    }

    /**
     * Upgrades the organization to a new plan.
     */
    public void upgradePlan(OrganizationPlan newPlan) {
        if (newPlan.ordinal() > this.plan.ordinal()) {
            this.plan = newPlan;
        } else {
            throw new IllegalArgumentException("Can only upgrade to a higher plan");
        }
    }

    /**
     * Deactivates the organization.
     */
    public void deactivate() {
        this.isActive = false;
    }
}
