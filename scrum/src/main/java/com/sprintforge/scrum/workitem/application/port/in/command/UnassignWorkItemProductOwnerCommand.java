package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record UnassignWorkItemProductOwnerCommand(
        UUID employeeId,
        UUID id
) {
}
