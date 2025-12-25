package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record CreateWorkItemRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "El proyecto es obligatorio")
        UUID projectId,

        UUID sprintId,
        UUID boardColumnId,

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
        String title,

        @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
        String description,

        @Size(max = 500, message = "Los criterios de aceptación no pueden tener más de 500 caracteres")
        String acceptanceCriteria,

        @Min(value = 0, message = "Los story points no pueden ser negativos")
        Integer storyPoints,

        @Min(value = 1, message = "La prioridad debe ser mayor o igual a 1")
        int priority,

        UUID developerId,
        UUID productOwnerId
) {
}
