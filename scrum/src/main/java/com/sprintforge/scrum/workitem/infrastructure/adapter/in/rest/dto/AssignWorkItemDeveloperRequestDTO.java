package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AssignWorkItemDeveloperRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "El desarrollador es obligatorio")
        UUID developerId
) {
}
