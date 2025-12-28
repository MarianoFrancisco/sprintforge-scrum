package com.sprintforge.scrum.workitem.application.port.in.query;

import java.util.UUID;

public record GetAllWorkItemsQuery(
        UUID projectId,
        UUID sprintId,
        UUID boardColumnId,
        Integer priority,
        UUID developerId,
        UUID productOwnerId,
        String searchTerm
) {
}
