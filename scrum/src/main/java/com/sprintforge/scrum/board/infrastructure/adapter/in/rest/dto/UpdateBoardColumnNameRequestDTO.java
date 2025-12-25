package com.sprintforge.scrum.board.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateBoardColumnNameRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotBlank(message = "El nombre de la columna es obligatorio.")
        @Size(max = 60, message = "El nombre de la columna no puede exceder los 60 caracteres.")
        String name
) {
}
