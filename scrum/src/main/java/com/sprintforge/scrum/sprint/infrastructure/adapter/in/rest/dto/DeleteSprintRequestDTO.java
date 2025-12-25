package com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteSprintRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId
) {
}
