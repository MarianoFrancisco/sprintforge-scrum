package com.sprintforge.scrum.project.application.port.out.event;

public interface ProjectEventPublisher {
    void publishProjectCreated(ProjectCreatedIntegrationEvent event);
}
