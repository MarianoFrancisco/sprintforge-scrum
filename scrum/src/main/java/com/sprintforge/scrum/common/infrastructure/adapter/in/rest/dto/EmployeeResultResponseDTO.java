package com.sprintforge.scrum.common.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

public record EmployeeResultResponseDTO(
        UUID id,
        String email,
        String fullName,
        String profileImage,
        String position
) {
}
