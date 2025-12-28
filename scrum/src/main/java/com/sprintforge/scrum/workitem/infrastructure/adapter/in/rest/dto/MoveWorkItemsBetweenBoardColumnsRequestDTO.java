package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;

public record MoveWorkItemsBetweenBoardColumnsRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "El sprint es obligatorio")
        UUID sprintId,

        @NotNull(message = "La columna destino es obligatoria")
        UUID targetBoardColumnId,

        @Min(value = 0, message = "La posición destino no puede ser negativa")
        int targetPosition,

        @NotNull(message = "La lista de work items es obligatoria")
        @Size(min = 1, message = "Debe mover al menos un work item")
        List<@NotNull UUID> ids
) {
}
