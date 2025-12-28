package com.sprintforge.scrum.board.infrastructure.adapter.in.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MoveBoardColumnRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "El sprint es obligatorio.")
        UUID sprintId,

        @NotEmpty(message = "No puede estar vacia la nueva posición de columna.")
        @Valid
        int newPosition
) {
}
