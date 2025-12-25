package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record UnassignProjectEmployeesRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "La lista de empleados es obligatoria")
        @Size(min = 1, message = "Debe desasignar al menos un empleado")
        Set<@NotNull(message = "El identificador del empleado no puede estar vacío") UUID> employeeIds
) {
}
