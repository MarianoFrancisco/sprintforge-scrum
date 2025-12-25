package com.sprintforge.scrum.sprint.application.port.in.command;

import java.time.Instant;
import java.util.UUID;

public record UpdateSprintDatesCommand(
        UUID employeeId,
        UUID id,
        Instant startDate,
        Instant endDate
) {
}
