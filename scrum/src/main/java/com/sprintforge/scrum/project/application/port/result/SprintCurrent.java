package com.sprintforge.scrum.project.application.port.result;

import java.time.Instant;
import java.util.UUID;

public record SprintCurrent(
        UUID sprintId,
        String name,
        String goal,
        String status,
        Instant startDate,
        Instant endDate
) {
}