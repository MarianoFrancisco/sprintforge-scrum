package com.sprintforge.scrum.sprint.application.port.in.query;

import java.util.UUID;

public record GetAllSprintsQuery(
        UUID projectId,
        String searchTerm,
        String status
) {
}