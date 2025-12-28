package com.sprintforge.scrum.project.application.mapper;

import com.sprintforge.scrum.project.application.port.out.event.ProjectCreatedIntegrationEvent;
import com.sprintforge.scrum.project.domain.Project;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ProjectIntegrationMapper {
    public ProjectCreatedIntegrationEvent from(
            UUID employeeId,
            Project project
    ) {
        if (project == null) {
            return null;
        }
        return new ProjectCreatedIntegrationEvent(
                employeeId,
                project.getId().value(),
                project.getProjectKey().value(),
                project.getName().value(),
                project.getDescription() != null ? project.getDescription().value() : null,
                project.getClient().value(),
                project.getArea().value(),
                project.getBudgetAmount() != null ? project.getBudgetAmount().value() : null,
                project.getContractAmount() != null ? project.getContractAmount().value() : null,
                project.isClosed(),
                project.isDeleted()
        );
    }
}
