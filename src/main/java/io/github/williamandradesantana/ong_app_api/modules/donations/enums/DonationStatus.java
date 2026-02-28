package io.github.williamandradesantana.ong_app_api.modules.donations.enums;

public enum DonationStatus {
    PENDING, CONFIRMED, REFUNDED;

    public static DonationStatus fromString(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Donation status invalid: " + value);

        try {
            return DonationStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Donation status invalid: " + value);
        }
    }
}
