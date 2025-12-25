package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;

public record MoveWorkItemsToSprintRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "La lista de work items es obligatoria")
        @Size(min = 1, message = "Debe mover al menos un work item")
        List<@NotNull UUID> ids,

        @NotNull(message = "El sprint es obligatorio")
        UUID sprintId
) {
}
