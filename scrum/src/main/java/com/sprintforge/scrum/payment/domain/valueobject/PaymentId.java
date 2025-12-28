package com.sprintforge.scrum.payment.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

import java.util.UUID;

public record PaymentId(UUID value) {
    public PaymentId {
        if (value == null) {
            throw new ValidationException("El identificador del pago no puede estar vacío");
        }
    }

    public static PaymentId of(UUID value) {
        return new PaymentId(value);
    }
}
