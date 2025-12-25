package com.sprintforge.scrum.common.application.port.out.rest.employee.command;

import java.util.Set;
import java.util.UUID;

public record ValidateEmployeesCommand(
        Set<UUID> employeeIds
) {
}
