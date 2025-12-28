package com.sprintforge.scrum.project.application.port.in.command;

import java.util.UUID;

public record UpdateProjectAreaCommand(
        UUID employeeId,
        UUID id,
        String area
) {
}
