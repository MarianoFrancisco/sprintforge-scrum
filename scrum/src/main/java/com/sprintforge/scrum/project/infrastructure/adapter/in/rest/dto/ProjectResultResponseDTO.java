package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import com.sprintforge.scrum.common.infrastructure.adapter.in.rest.dto.EmployeeResultResponseDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ProjectResultResponseDTO(
        UUID id,
        String projectKey,
        String name,
        String description,
        String client,
        String area,
        BigDecimal budgetAmount,
        BigDecimal contractAmount,
        boolean isClosed,
        boolean isDeleted,
        Instant createdAt,
        Instant updatedAt,
        List<EmployeeResultResponseDTO> employees
) {
}
