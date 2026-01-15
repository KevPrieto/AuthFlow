package com.accessflow.domain;

/**
 * Represents the subscription plan of an organization.
 * Plans have explicit tier ordering for upgrade/downgrade logic.
 */
public enum OrganizationPlan {
    /**
     * Free tier with limited features
     */
    FREE(0),

    /**
     * Standard paid plan
     */
    STANDARD(1),

    /**
     * Premium plan with advanced features
     */
    PREMIUM(2),

    /**
     * Enterprise plan with custom features and support
     */
    ENTERPRISE(3);

    private final int tier;

    OrganizationPlan(int tier) {
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    public boolean isHigherThan(OrganizationPlan other) {
        return this.tier > other.tier;
    }

    public boolean isLowerThan(OrganizationPlan other) {
        return this.tier < other.tier;
    }
}
