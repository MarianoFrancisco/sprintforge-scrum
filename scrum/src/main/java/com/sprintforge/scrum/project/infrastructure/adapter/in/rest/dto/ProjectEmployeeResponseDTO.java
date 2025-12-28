package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import com.sprintforge.scrum.project.domain.valueobject.ProjectEmployeeAssignment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record ProjectEmployeeResponseDTO(
        UUID employeeId,
        Instant assignedAt
) {
}
