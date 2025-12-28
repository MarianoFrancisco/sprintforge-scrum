package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record UpdateWorkItemTitleCommand(
        UUID employeeId,
        UUID id,
        String title
) {
}
