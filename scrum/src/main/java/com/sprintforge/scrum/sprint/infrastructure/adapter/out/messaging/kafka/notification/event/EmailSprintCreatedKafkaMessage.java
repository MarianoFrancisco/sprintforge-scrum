package com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.notification.event;

public record EmailSprintCreatedKafkaMessage(
        String email,
        String fullName,
        String sprintName
) {
}
