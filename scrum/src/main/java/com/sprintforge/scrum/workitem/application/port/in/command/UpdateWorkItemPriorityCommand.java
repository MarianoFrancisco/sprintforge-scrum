package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record UpdateWorkItemPriorityCommand(
        UUID employeeId,
        UUID id,
        int priority
) {
}
