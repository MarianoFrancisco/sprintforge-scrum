package com.sprintforge.scrum.project.infrastructure.adapter.out.messaging.kafka.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProjectCreatedKafkaMessage(
        String entityType,
        String eventType,
        String message,
        Instant occurredAt,
        UUID projectId,
        String projectKey,
        String name,
        String description,
        String client,
        String area,
        BigDecimal budgetAmount,
        BigDecimal contractAmount,
        boolean isClosed,
        boolean isDeleted
) {
}
