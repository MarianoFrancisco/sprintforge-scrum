package com.sprintforge.scrum.board.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MoveBoardColumnRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "No puede estar vacia la columna a mover.")
        UUID columnId,

        int newPosition
) {
}
