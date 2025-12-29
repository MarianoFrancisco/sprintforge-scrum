package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

public record EmployeeDTO(
        UUID id,
        String email,
        String fullName,
        String profileImage,
        String position
) {
}
