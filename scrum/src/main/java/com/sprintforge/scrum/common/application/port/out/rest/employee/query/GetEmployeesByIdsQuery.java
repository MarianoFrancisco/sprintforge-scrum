package com.sprintforge.scrum.common.application.port.out.rest.employee.query;

import java.util.Set;
import java.util.UUID;

public record GetEmployeesByIdsQuery(
        Set<UUID> employeeIds
) {
}
