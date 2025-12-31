package com.sprintforge.scrum.sprint.application.port.out.event.notification;

public interface NotificationEventPublisher {
    void publishEmailSprintCreated(EmailSprintCreatedIntegrationEvent event);
}
