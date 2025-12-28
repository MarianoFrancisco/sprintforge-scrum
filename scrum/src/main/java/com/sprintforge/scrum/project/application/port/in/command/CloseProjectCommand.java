package com.sprintforge.scrum.project.application.port.in.command;

import java.util.UUID;

public record CloseProjectCommand(
        UUID id,
        UUID employeeId
) {
}