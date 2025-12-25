package com.sprintforge.scrum.sprint.application.port.in.command;

import java.util.UUID;

public record CompleteSprintCommand(
        UUID employeeId,
        UUID id,
        UUID targetSprintId
) {
}
