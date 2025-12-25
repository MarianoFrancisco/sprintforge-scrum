package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateProjectDescriptionRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
        String description
) {
}
