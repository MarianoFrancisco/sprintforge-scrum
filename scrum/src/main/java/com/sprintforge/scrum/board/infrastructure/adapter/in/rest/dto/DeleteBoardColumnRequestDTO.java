package com.sprintforge.scrum.board.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeleteBoardColumnRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "La columna a pasar las historias de usuario es obligatoria")
        UUID targetBoardColumnId
) {
}
