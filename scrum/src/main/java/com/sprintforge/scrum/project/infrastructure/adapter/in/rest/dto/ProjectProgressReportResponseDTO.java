package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import java.util.List;

public record ProjectProgressReportResponseDTO(
        long totalProjects,
        long totalSprints,
        long startedSprints,
        long createdSprints,
        long completedSprints,
        List<ProjectProgressItemDTO> projects
) {
}
