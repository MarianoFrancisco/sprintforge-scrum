package com.sprintforge.scrum.project.infrastructure.adapter.out.messaging.kafka.mapper;

import com.sprintforge.scrum.project.application.port.out.event.ProjectCreatedIntegrationEvent;
import com.sprintforge.scrum.project.infrastructure.adapter.out.messaging.kafka.event.ProjectCreatedKafkaMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectKafkaEventMapper {

    public ProjectCreatedKafkaMessage toMessage(ProjectCreatedIntegrationEvent event) {
        return new ProjectCreatedKafkaMessage(
                event.employeeId(),
                event.projectId(),
                event.projectKey(),
                event.name(),
                event.description(),
                event.client(),
                event.area(),
                event.budgetAmount(),
                event.contractAmount(),
                event.isClosed(),
                event.isDeleted()
        );
    }
}
