package com.sprintforge.scrum.project.application.port.out.persistence.raw;

import java.time.Instant;
import java.util.UUID;

public record StartedSprintRaw(
        UUID sprintId,
        String name,
        String goal,
        String status,
        Instant startDate,
        Instant endDate
) {
}
