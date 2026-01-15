package com.accessflow.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "memberships",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_org", columnNames = {"user_id", "organization_id"})
    },
    indexes = {
        @Index(name = "idx_membership_user", columnList = "user_id"),
        @Index(name = "idx_membership_org", columnList = "organization_id"),
        @Index(name = "idx_membership_role", columnList = "role_id")
    }
)
public class MembershipJpaEntity extends BaseJpaEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_membership_user"))
    private UserJpaEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false, foreignKey = @ForeignKey(name = "fk_membership_org"))
    private OrganizationJpaEntity organization;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false, foreignKey = @ForeignKey(name = "fk_membership_role"))
    private RoleJpaEntity role;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public MembershipJpaEntity() {
    }

    public UserJpaEntity getUser() { return user; }
    public void setUser(UserJpaEntity user) { this.user = user; }
    public OrganizationJpaEntity getOrganization() { return organization; }
    public void setOrganization(OrganizationJpaEntity organization) { this.organization = organization; }
    public RoleJpaEntity getRole() { return role; }
    public void setRole(RoleJpaEntity role) { this.role = role; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
