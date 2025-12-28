package com.sprintforge.scrum.payment.infrastructure.adapter.in.rest.dto;

import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto.ProjectResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentResponseDTO(
        UUID id,
        LocalDate date,
        BigDecimal amount,
        String method,
        String reference,
        String note,
        Instant createdAt,
        ProjectResponseDTO project
) {
}
