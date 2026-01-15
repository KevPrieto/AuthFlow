package com.accessflow.infrastructure.persistence.entity;

import com.accessflow.domain.OrganizationPlan;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "organizations", indexes = {
    @Index(name = "idx_org_name", columnList = "name"),
    @Index(name = "idx_org_slug", columnList = "slug")
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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public OrganizationPlan getPlan() { return plan; }
    public void setPlan(OrganizationPlan plan) { this.plan = plan; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public Set<MembershipJpaEntity> getMemberships() { return memberships; }
    public void setMemberships(Set<MembershipJpaEntity> memberships) { this.memberships = memberships; }
}
