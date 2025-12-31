package com.sprintforge.scrum.sprint.application.port.out.event;

public interface SprintEventPublisher {
    void publishSprintCreated(SprintCreatedIntegrationEvent event);
}
