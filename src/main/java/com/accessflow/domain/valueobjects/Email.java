package com.accessflow.domain.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value object representing an email address.
 * Ensures email validity at the type level.
 */
public final class Email {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final String value;

    public Email(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        String trimmed = value.trim().toLowerCase();

        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }

        if (trimmed.length() > 255) {
            throw new IllegalArgumentException("Email cannot exceed 255 characters");
        }

        this.value = trimmed;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
