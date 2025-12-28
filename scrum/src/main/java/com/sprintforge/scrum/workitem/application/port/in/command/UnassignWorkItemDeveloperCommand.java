package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record UnassignWorkItemDeveloperCommand(
        UUID employeeId,
        UUID id
) {
}
