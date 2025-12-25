package com.sprintforge.scrum.project.application.port.in.command;

import java.util.UUID;

public record OpenProjectCommand(
        UUID employeeId,
        UUID id
) {
}