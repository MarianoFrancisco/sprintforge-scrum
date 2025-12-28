package com.sprintforge.scrum.payment.application.port.in.query;

import java.time.LocalDate;
import java.util.UUID;

public record GetAllPaymentsQuery(
        String searchTerm,
        UUID projectId,
        String method,
        LocalDate fromDate,
        LocalDate toDate
) {
}
