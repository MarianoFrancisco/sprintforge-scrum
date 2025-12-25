package com.sprintforge.scrum.payment.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

import java.util.UUID;

public class PaymentNotFoundException extends BusinessException {
    public PaymentNotFoundException(UUID id) {
        super("El pago con identificador " + id.toString() + " no fue encontrado.");
    }
}
