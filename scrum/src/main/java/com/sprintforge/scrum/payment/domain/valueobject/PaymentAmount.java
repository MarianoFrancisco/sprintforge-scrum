package com.sprintforge.scrum.payment.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public record PaymentAmount(BigDecimal value) {
    public PaymentAmount {
        if (value == null) {
            throw new ValidationException("El monto del pago no puede estar vacío");
        }
        if (value.compareTo(ZERO) <= 0) {
            throw new ValidationException("El monto del pago debe ser mayor a 0");
        }
    }

    public static PaymentAmount of(BigDecimal value) {
        return new PaymentAmount(value);
    }
}
