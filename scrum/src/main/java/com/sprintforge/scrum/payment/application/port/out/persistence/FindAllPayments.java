package com.sprintforge.scrum.payment.application.port.out.persistence;

import com.sprintforge.scrum.payment.application.port.in.query.GetAllPaymentsQuery;
import com.sprintforge.scrum.payment.domain.Payment;

import java.util.List;

public interface FindAllPayments {
    List<Payment> findAll(GetAllPaymentsQuery query);
}
