package com.sprintforge.scrum.common.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

public record EmployeePositionResponseDTO(
        UUID id,
        String name,
        String description
) {
}
