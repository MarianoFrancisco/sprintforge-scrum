package com.sprintforge.scrum.project.application.mapper;

import com.sprintforge.scrum.project.application.port.in.command.CreateProjectCommand;
import com.sprintforge.scrum.project.domain.Project;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectMapper {
    public Project toDomain(CreateProjectCommand command) {
        if (command == null) {
            return null;
        }
        Project project = new Project(
                command.projectKey(),
                command.name(),
                command.description(),
                command.client(),
                command.area(),
                command.budgetAmount(),
                command.contractAmount()
        );
        project.setEmployees(command.employeeIds());
        return project;
    }
}
