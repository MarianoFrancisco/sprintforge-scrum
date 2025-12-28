package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record AssignWorkItemProductOwnerCommand(
        UUID employeeId,
        UUID id,
        UUID productOwnerId
) {
}
