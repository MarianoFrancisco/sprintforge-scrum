package com.sprintforge.scrum.payment.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreatePaymentRequestDTO(
        @NotNull(message = "El empleado que realiza la acción es obligatorio")
        UUID employeeId,

        @NotNull(message = "El proyecto es obligatorio")
        UUID projectId,

        @NotNull(message = "La fecha del pago es obligatoria")
        LocalDate date,

        @NotNull(message = "El monto es obligatorio")
        @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0")
        @Digits(integer = 10, fraction = 2, message = "El monto puede tener como máximo 2 decimales")
        BigDecimal amount,

        @NotBlank(message = "El método de pago es obligatorio")
        @Pattern(
                regexp = "CASH|TRANSFER|CARD",
                message = "El método de pago debe ser: efectivo, transferencia o tarjeta"
        )
        String method,

        @Size(max = 60, message = "La referencia no puede superar los 60 caracteres")
        String reference,

        @Size(max = 255, message = "La nota no puede superar los 255 caracteres")
        String note
) {
}
