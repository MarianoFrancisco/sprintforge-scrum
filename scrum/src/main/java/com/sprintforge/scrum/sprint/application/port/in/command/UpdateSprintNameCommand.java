package com.sprintforge.scrum.sprint.application.port.in.command;

import java.util.UUID;

public record UpdateSprintNameCommand(
        UUID employeeId,
        UUID id,
        String name
) {
}
