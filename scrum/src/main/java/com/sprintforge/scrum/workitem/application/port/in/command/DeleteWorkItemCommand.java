package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record DeleteWorkItemCommand(
        UUID employeeId,
        UUID id
) {
}
