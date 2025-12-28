package com.sprintforge.scrum.project.application.port.in.command;

import java.util.UUID;

public record UpdateProjectDescriptionCommand(
        UUID employeeId,
        UUID id,
        String description
) {
}
