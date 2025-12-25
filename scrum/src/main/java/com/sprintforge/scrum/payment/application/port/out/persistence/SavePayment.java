package com.sprintforge.scrum.payment.application.port.out.persistence;

import com.sprintforge.scrum.payment.domain.Payment;

public interface SavePayment {
    Payment save(Payment payment);
}
