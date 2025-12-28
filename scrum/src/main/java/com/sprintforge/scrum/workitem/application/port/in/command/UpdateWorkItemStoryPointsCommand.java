package com.sprintforge.scrum.workitem.application.port.in.command;

import java.util.UUID;

public record UpdateWorkItemStoryPointsCommand(
        UUID employeeId,
        UUID id,
        Integer storyPoints
) {
}
