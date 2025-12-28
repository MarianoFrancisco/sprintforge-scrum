package com.sprintforge.scrum.payment.application.port.in.command;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreatePaymentCommand(
        UUID employeeId,
        UUID projectId,
        LocalDate date,
        BigDecimal amount,
        String method,
        String reference,
        String note
) {
}
