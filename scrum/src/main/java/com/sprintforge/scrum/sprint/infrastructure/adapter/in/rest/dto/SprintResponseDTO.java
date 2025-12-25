package com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.dto;

import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto.ProjectResponseDTO;
import com.sprintforge.scrum.sprint.domain.valueobject.SprintStatus;

import java.time.Instant;
import java.util.UUID;

public record SprintResponseDTO(
        UUID id,
        String name,
        String goal,
        SprintStatus status,
        Instant startDate,
        Instant endDate,
        boolean isDeleted,
        Instant createdAt,
        Instant updatedAt,
        ProjectResponseDTO project
) {
}
