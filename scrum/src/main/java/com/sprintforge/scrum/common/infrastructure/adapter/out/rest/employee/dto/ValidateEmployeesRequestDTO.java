package com.sprintforge.scrum.common.infrastructure.adapter.out.rest.employee.dto;

import java.util.Set;
import java.util.UUID;

public record ValidateEmployeesRequestDTO(
        Set<UUID> ids
) {
}
