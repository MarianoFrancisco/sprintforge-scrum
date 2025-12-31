package com.sprintforge.scrum.sprint.application.port.out.event.notification;

public record EmailSprintCreatedIntegrationEvent(
        String email,
        String fullName,
        String sprintName
) {
}
