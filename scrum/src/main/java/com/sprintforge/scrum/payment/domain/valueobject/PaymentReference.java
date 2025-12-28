package com.sprintforge.scrum.payment.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record PaymentReference(String value) {

    private static final int MAX_LENGTH = 60;

    public PaymentReference {
        if (value != null) {
            String normalized = value.trim().replaceAll("\\s+", " ");

            if (normalized.length() > MAX_LENGTH) {
                throw new ValidationException("La referencia del pago no puede exceder los " + MAX_LENGTH + " caracteres");
            }

            value = normalized;
        }
    }

    public static PaymentReference of(String value) {
        return new PaymentReference(value);
    }
}
