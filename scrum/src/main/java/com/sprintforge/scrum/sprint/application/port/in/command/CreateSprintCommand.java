package com.sprintforge.scrum.sprint.application.port.in.command;

import java.time.Instant;
import java.util.UUID;

public record CreateSprintCommand(
        UUID employeeId,
        UUID projectId,
        String name,
        String goal,
        Instant startDate,
        Instant endDate
) {
}
