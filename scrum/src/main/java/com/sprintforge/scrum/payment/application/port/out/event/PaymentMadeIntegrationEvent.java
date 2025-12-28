package com.sprintforge.scrum.payment.application.port.out.event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentMadeIntegrationEvent(
        UUID paymentId,
        UUID projectId,
        LocalDate date,
        BigDecimal amount,
        String method
) {
}
