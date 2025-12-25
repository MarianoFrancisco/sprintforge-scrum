package com.sprintforge.scrum.board.infrastructure.adapter.in.rest.dto;

import com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.dto.SprintResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record BoardColumnResponseDTO(
        UUID id,
        String name,
        int position,
        boolean isFinal,
        boolean isDeleted,
        Instant createdAt,
        Instant updatedAt,
        SprintResponseDTO sprint
) {
}
