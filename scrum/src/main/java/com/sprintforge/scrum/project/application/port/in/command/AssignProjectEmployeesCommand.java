package com.sprintforge.scrum.project.application.port.in.command;

import java.util.Set;
import java.util.UUID;

public record AssignProjectEmployeesCommand(
        UUID employeeId,
        UUID id,
        Set<UUID> employeeIds
) {
}
