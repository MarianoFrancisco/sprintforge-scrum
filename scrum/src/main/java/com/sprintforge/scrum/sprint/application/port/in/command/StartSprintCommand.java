package com.sprintforge.scrum.sprint.application.port.in.command;

import java.util.UUID;

public record StartSprintCommand(
        UUID employeeId,
        UUID id
) {
}
