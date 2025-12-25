package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record UpdateWorkItemDescriptionRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
        String description
) {
}
