package com.sprintforge.scrum.project.application.port.in.command;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProjectAmountsCommand(
        UUID employeeId,
        UUID id,
        BigDecimal budgetAmount,
        BigDecimal contractAmount
) {
}
