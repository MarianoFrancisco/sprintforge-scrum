package com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateSprintGoalRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @Size(max = 500, message = "La meta no puede exceder 500 caracteres")
        String goal
) {
}
