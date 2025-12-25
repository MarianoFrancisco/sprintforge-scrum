package com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.dto;

import com.sprintforge.common.infrastructure.validation.END_AFTER_START;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;


@END_AFTER_START(start = "startDate", end = "endDate", message = "endDate debe ser posterior a startDate")
public record CreateSprintRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "El proyecto es obligatorio")
        UUID projectId,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 120, message = "El nombre no puede exceder 120 caracteres")
        String name,

        @Size(max = 500, message = "La meta no puede exceder 500 caracteres")
        String goal,

        @NotNull(message = "La fecha de inicio es obligatoria")
        Instant startDate,

        @NotNull(message = "La fecha de fin es obligatoria")
        Instant endDate
) {
}
