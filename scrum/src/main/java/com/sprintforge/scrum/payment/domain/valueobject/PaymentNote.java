package com.sprintforge.scrum.payment.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record PaymentNote(String value) {

    private static final int MAX_LENGTH = 255;

    public PaymentNote {
        if (value != null && value.length() > MAX_LENGTH) {
            throw new ValidationException("La nota del pago no puede exceder los " + MAX_LENGTH + " caracteres");
        }
    }

    public static PaymentNote of(String value) {
        return new PaymentNote(value);
    }
}
