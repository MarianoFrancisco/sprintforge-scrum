package com.sprintforge.scrum.payment.domain.valueobject;

import java.time.LocalDate;

import static java.time.LocalDate.now;

public record PaymentDate(LocalDate value) {
    public PaymentDate {
        if (value == null) {
            value = now();
        }
    }

    public static PaymentDate of(LocalDate value) {
        return new PaymentDate(value);
    }
}
