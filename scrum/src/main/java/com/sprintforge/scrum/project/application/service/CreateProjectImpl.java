package com.sprintforge.scrum.project.application.service;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployees;
import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployeesCommand;
import com.sprintforge.scrum.common.application.port.out.rest.employee.query.GetEmployeesByIds;
import com.sprintforge.scrum.common.application.port.out.rest.employee.query.GetEmployeesByIdsQuery;
import com.sprintforge.scrum.common.application.service.support.EmployeeQuerySupport;
import com.sprintforge.scrum.project.application.exception.DuplicateProjectException;
import com.sprintforge.scrum.project.application.mapper.ProjectIntegrationMapper;
import com.sprintforge.scrum.project.application.mapper.ProjectMapper;
import com.sprintforge.scrum.project.application.port.in.command.CreateProject;
import com.sprintforge.scrum.project.application.port.in.command.CreateProjectCommand;
import com.sprintforge.scrum.project.application.port.out.event.ProjectEventPublisher;
import com.sprintforge.scrum.project.application.port.out.persistence.ExistProjectByProjectKey;
import com.sprintforge.scrum.project.application.port.out.persistence.ExistProjectByName;
import com.sprintforge.scrum.project.application.port.out.persistence.SaveProject;
import com.sprintforge.scrum.project.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateProjectImpl implements CreateProject {

    private final ValidateEmployees validateEmployees;
    private final EmployeeQuerySupport employeeQuerySupport;
    private final ExistProjectByProjectKey existProjectByProjectKey;
    private final ExistProjectByName existProjectByName;
    private final SaveProject saveProject;
    private final ProjectEventPublisher projectEventPublisher;

    @Override
    public Project handle(CreateProjectCommand command) {
        command.employeeIds().add(command.employeeId());

        if (existProjectByProjectKey.existsByProjectKey(command.projectKey())) {
            throw DuplicateProjectException.byProjectKey(command.projectKey());
        }
        if (existProjectByName.existsByName(command.name())) {
            throw DuplicateProjectException.byName(command.name());
        }
        validateEmployees.validate(new ValidateEmployeesCommand(command.employeeIds()));
        EmployeeResult employee =
                employeeQuerySupport.getEmployee(command.employeeId());

        Project project = ProjectMapper.toDomain(
                command
        );
        Project savedProject = saveProject.save(project);

        projectEventPublisher.publishProjectCreated(
                ProjectIntegrationMapper.from(
                        employee,
                        savedProject
                )
        );

        return savedProject;
    }
}
