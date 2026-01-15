package com.accessflow.domain;

/**
 * Represents the status of a user account.
 */
public enum UserStatus {
    /**
     * User account is active and can authenticate
     */
    ACTIVE,

    /**
     * User account is suspended and cannot authenticate
     */
    SUSPENDED,

    /**
     * User account is pending verification (e.g., email confirmation)
     */
    PENDING,

    /**
     * User account has been deleted (soft delete)
     */
    DELETED
}
