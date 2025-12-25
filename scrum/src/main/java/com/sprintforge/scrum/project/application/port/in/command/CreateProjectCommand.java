package com.sprintforge.scrum.project.application.port.in.command;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CreateProjectCommand(
        UUID employeeId,
        String projectKey,
        String name,
        String description,
        String client,
        String area,
        BigDecimal budgetAmount,
        BigDecimal contractAmount,
        Set<UUID> employeeIds
) {
}