package com.sprintforge.scrum.project.application.port.result;

import com.sprintforge.scrum.common.application.port.result.EmployeeResult;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ProjectResult(
        UUID id,
        String projectKey,
        String name,
        String description,
        String client,
        String area,
        BigDecimal budgetAmount,
        BigDecimal contractAmount,
        boolean isClosed,
        boolean isDeleted,
        Instant createdAt,
        Instant updatedAt,
        List<EmployeeResult> employees
) {
}
