package com.sprintforge.scrum.project.application.port.out.persistence.raw;

import java.util.List;

public record ProjectProgressReportRaw(
        long totalProjects,
        long totalSprints,
        long startedSprints,
        long createdSprints,
        long completedSprints,

        List<ProjectRawItem> projects
) {
}
