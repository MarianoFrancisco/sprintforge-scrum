package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProjectAmountsRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @DecimalMin(value = "0.00", message = "El presupuesto no puede ser negativo")
        @Digits(integer = 10, fraction = 2, message = "El presupuesto puede tener como máximo 2 decimales")
        BigDecimal budgetAmount,

        @DecimalMin(value = "0.00", message = "El monto del contrato no puede ser negativo")
        @Digits(integer = 10, fraction = 2, message = "El monto del contrato puede tener como máximo 2 decimales")
        BigDecimal contractAmount
) {
}
