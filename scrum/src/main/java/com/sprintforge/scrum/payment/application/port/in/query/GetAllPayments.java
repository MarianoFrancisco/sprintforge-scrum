package com.sprintforge.scrum.payment.application.port.in.query;

import com.sprintforge.scrum.payment.domain.Payment;

import java.util.List;

public interface GetAllPayments {
    List<Payment> handle(GetAllPaymentsQuery query);
}
