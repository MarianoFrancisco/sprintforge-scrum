package com.sprintforge.scrum.project.application.port.in.command;

import java.util.UUID;

public record UpdateProjectNameCommand(
        UUID employeeId,
        UUID id,
        String name
) {
}
