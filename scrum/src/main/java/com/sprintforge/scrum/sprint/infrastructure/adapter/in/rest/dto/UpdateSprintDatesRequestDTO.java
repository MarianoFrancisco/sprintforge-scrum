package com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.dto;

import com.sprintforge.common.infrastructure.validation.END_AFTER_START;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.UUID;

@END_AFTER_START(start = "startDate", end = "endDate", message = "endDate debe ser posterior a startDate")
public record UpdateSprintDatesRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "La fecha de inicio es obligatoria")
        Instant startDate,

        @NotNull(message = "La fecha de fin es obligatoria")
        Instant endDate
) {
}
