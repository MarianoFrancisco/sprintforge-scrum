package com.sprintforge.scrum.project.application.mapper;

import com.sprintforge.scrum.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.project.application.port.result.ProjectResult;
import com.sprintforge.scrum.project.domain.Project;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ProjectResultMapper {

    public ProjectResult toResult(
            Project project,
            List<EmployeeResult> employeeResults
    ) {
        if (project == null) return null;

        return new ProjectResult(
                project.getId().value(),
                project.getProjectKey().value(),
                project.getName().value(),
                project.getDescription() != null ? project.getDescription().value() : null,
                project.getClient().value(),
                project.getArea().value(),
                project.getBudgetAmount() != null ? project.getBudgetAmount().value() : null,
                project.getContractAmount() != null ? project.getContractAmount().value() : null,
                project.isClosed(),
                project.isDeleted(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                employeeResults
        );
    }
}
