package com.sprintforge.scrum.payment.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public enum PaymentMethod {
    CASH,
    TRANSFER,
    CARD;

    public static PaymentMethod safeValueOf(String value) {
        if (value == null) {
            throw new ValidationException("El método de pago no puede estar vacío");
        }
        String normalized = value.trim().toUpperCase();
        if (normalized.isBlank()) {
            throw new ValidationException("El método de pago no puede estar vacío");
        }
        try {
            return PaymentMethod.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("El método de pago es inválido: " + value);
        }
    }
}
