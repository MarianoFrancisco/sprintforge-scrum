package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record UpdateWorkItemDescriptionCommand(
        UUID employeeId,
        UUID id,
        String description
) {
}
