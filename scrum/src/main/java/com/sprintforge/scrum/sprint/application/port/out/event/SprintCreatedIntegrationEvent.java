package com.sprintforge.scrum.sprint.application.port.out.event;

import java.time.Instant;
import java.util.UUID;

public record SprintCreatedIntegrationEvent(
        String entityType,
        String eventType,
        String message,
        Instant occurredAt,
        UUID projectId,
        UUID sprintId
) {
}
