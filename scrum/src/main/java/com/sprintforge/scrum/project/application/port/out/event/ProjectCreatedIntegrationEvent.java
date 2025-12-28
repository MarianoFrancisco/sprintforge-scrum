package com.sprintforge.scrum.project.application.port.out.event;

import java.math.BigDecimal;
import java.util.UUID;

public record ProjectCreatedIntegrationEvent(
        UUID employeeId,
        UUID projectId,
        String projectKey,
        String name,
        String description,
        String client,
        String area,
        BigDecimal budgetAmount,
        BigDecimal contractAmount,
        boolean isClosed,
        boolean isDeleted
) {
}
