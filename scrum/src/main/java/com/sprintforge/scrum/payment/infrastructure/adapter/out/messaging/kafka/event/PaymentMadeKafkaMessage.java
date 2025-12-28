package com.sprintforge.scrum.payment.infrastructure.adapter.out.messaging.kafka.event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentMadeKafkaMessage(
        UUID paymentId,
        UUID projectId,
        LocalDate date,
        BigDecimal amount,
        String method
) {
}
