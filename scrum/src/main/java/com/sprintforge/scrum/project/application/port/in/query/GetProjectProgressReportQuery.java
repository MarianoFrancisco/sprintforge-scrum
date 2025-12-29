package com.sprintforge.scrum.project.application.port.in.query;

import java.util.UUID;

public record GetProjectProgressReportQuery(
        UUID projectId
) {
}
