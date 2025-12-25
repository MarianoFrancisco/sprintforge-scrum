package com.sprintforge.scrum.project.application.port.in.command;

import java.util.UUID;

public record UpdateProjectClientCommand(
        UUID employeeId,
        UUID id,
        String client
) {
}
