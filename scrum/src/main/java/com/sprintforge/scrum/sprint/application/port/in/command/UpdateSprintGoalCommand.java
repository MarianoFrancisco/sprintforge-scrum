package com.sprintforge.scrum.sprint.application.port.in.command;

import java.util.UUID;

public record UpdateSprintGoalCommand(
        UUID employeeId,
        UUID id,
        String goal
) {
}
