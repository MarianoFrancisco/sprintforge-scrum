package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto;

import com.sprintforge.scrum.board.infrastructure.adapter.in.rest.dto.BoardColumnResponseDTO;
import com.sprintforge.scrum.common.infrastructure.adapter.in.rest.dto.EmployeeResultResponseDTO;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto.ProjectResponseDTO;
import com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.dto.SprintResponseDTO;

import java.time.Instant;
import java.util.UUID;

public record WorkItemResultResponseDTO(
        UUID id,
        int position,
        String title,
        String description,
        String acceptanceCriteria,
        Integer storyPoints,
        int priority,
        EmployeeResultResponseDTO developerId,
        EmployeeResultResponseDTO productOwnerId,
        boolean isDeleted,
        Instant createdAt,
        Instant updatedAt,
        ProjectResponseDTO project,
        SprintResponseDTO sprint,
        BoardColumnResponseDTO boardColumn
) {
}
