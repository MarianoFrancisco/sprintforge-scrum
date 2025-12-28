package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record UpdateWorkItemPriorityRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @Min(value = 1, message = "La prioridad debe ser mayor o igual a 1")
        int priority
) {
}
