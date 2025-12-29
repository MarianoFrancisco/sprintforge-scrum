package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import java.util.List;
import java.util.UUID;

public record ProjectProgressItemDTO(
        UUID projectId,
        String projectKey,
        String projectName,

        long backlogCount,
        long pendingCount,
        long inProgressCount,
        long completedCount,
        long totalStories,

        double progressPercentage,

        long totalSprints,
        long startedSprints,
        long createdSprints,
        long completedSprints,

        List<SprintCurrentDTO> currentSprints,
        List<EmployeeDTO> members
) {
}
