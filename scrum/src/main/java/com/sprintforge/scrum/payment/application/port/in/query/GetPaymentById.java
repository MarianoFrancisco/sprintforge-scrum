package com.sprintforge.scrum.payment.application.port.in.query;

import com.sprintforge.scrum.payment.domain.Payment;

public interface GetPaymentById {
    Payment handle(GetPaymentByIdQuery query);
}
