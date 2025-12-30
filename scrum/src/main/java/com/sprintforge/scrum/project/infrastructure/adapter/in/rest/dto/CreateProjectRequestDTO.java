package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CreateProjectRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotBlank(message = "La clave del proyecto es obligatoria")
        @Size(max = 10, message = "La clave no puede tener más de 10 caracteres")
        String projectKey,

        @NotBlank(message = "El nombre del proyecto es obligatorio")
        @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
        String name,

        @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
        String description,

        @NotBlank(message = "El nombre del cliente es obligatorio")
        @Size(max = 100, message = "El nombre del cliente no puede tener más de 100 caracteres")
        String client,

        @NotBlank(message = "El área es obligatoria")
        @Size(max = 80, message = "El área no puede tener más de 80 caracteres")
        String area,

        @DecimalMin(value = "0.00", message = "El presupuesto no puede ser negativo")
        @Digits(integer = 10, fraction = 2, message = "El presupuesto puede tener como máximo 2 decimales")
        BigDecimal budgetAmount,

        @DecimalMin(value = "0.00", message = "El monto del contrato no puede ser negativo")
        @Digits(integer = 10, fraction = 2, message = "El monto del contrato puede tener como máximo 2 decimales")
        BigDecimal contractAmount,

        @NotEmpty(message = "Debe asignar al menos un empleado")
        Set<@NotNull(message = "El identificador del empleado no puede estar vacío") UUID> employeeIds
) {
}
