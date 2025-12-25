package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record UpdateWorkItemTitleRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
        String title
) {
}
