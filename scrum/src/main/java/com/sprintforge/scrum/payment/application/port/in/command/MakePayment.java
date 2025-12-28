package com.sprintforge.scrum.payment.application.port.in.command;

import com.sprintforge.scrum.payment.domain.Payment;

public interface MakePayment {
    Payment handle(MakePaymentCommand command);
}
