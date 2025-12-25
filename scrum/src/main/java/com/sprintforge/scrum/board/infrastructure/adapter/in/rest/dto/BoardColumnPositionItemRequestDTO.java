package com.sprintforge.scrum.board.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BoardColumnPositionItemRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "La columna es obligatoria.")
        UUID id,

        @Min(value = 0, message = "La posición no puede ser negativa.")
        int position
) {
}
