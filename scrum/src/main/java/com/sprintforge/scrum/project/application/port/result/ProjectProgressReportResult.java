package com.sprintforge.scrum.project.application.port.result;

import java.util.List;

public record ProjectProgressReportResult(
        long totalProjects,
        long totalSprints,
        long startedSprints,
        long createdSprints,
        long completedSprints,

        List<ProjectProgressItem> projects
) {
}
