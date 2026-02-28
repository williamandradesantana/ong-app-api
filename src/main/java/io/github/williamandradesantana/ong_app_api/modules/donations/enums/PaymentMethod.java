package io.github.williamandradesantana.ong_app_api.modules.donations.enums;

public enum PaymentMethod {
    PIX, CREDIT_CARD, DEBIT_CARD;

    public static PaymentMethod fromString(String value) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException("Payment method invalid: " + value);

        try {
            return PaymentMethod.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Payment method invalid: " + value);
        }
    }
}
