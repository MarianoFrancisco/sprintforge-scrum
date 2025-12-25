package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record ProjectResponseDTO(
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
        Set<ProjectEmployeeResponseDTO> assignments
) {
}
