package com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.event;

import java.time.Instant;
import java.util.UUID;

public record SprintCompletedKafkaMessage(
        String entityType,
        String eventType,
        String message,
        Instant occurredAt,
        UUID projectId,
        UUID sprintId
) {
}
