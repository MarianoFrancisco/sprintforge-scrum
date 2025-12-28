package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record UpdateWorkItemAcceptanceCriteriaCommand(
        UUID employeeId,
        UUID id,
        String acceptanceCriteria
) {
}
