package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateProjectNameRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotBlank(message = "El nombre del proyecto es obligatorio")
        @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
        String name
) {
}
