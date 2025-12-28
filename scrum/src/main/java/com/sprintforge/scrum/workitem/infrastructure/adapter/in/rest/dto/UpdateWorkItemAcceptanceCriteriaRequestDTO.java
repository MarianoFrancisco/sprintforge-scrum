package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record UpdateWorkItemAcceptanceCriteriaRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @Size(max = 500, message = "Los criterios de aceptación no pueden tener más de 500 caracteres")
        String acceptanceCriteria
) {
}
