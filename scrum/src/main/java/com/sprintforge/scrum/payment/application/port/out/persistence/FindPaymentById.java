package com.sprintforge.scrum.payment.application.port.out.persistence;

import com.sprintforge.scrum.payment.domain.Payment;

import java.util.Optional;
import java.util.UUID;

public interface FindPaymentById {
    Optional<Payment> findById(UUID id);
}
