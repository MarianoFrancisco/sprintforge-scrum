package com.sprintforge.scrum.project.application.mapper;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.project.application.port.out.event.ProjectCreatedIntegrationEvent;
import com.sprintforge.scrum.project.domain.Project;
import lombok.experimental.UtilityClass;

import java.util.UUID;

import static java.time.Instant.now;

@UtilityClass
public class ProjectIntegrationMapper {
    public ProjectCreatedIntegrationEvent from(
            EmployeeResult employee,
            Project project
    ) {
        if (project == null) {
            return null;
        }
        return new ProjectCreatedIntegrationEvent(
                "PROJECT",
                "PROJECT_CREATED",
                "El empleado " + employee.fullName() + " ha creado el proyecto " + project.getName().value(),
                now(),
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
