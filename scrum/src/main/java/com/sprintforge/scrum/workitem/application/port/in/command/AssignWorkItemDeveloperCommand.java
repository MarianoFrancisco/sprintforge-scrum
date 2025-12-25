package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record AssignWorkItemDeveloperCommand(
        UUID employeeId,
        UUID id,
        UUID developerId
) {
}
