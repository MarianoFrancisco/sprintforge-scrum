package com.sprintforge.scrum.project.application.service;

import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployees;
import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployeesCommand;
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

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateProjectImpl implements CreateProject {

    private final ValidateEmployees validateEmployees;
    private final ExistProjectByProjectKey existProjectByProjectKey;
    private final ExistProjectByName existProjectByName;
    private final SaveProject saveProject;
    private final ProjectEventPublisher projectEventPublisher;

    @Override
    public Project handle(CreateProjectCommand command) {
        if (existProjectByProjectKey.existsByProjectKey(command.projectKey())) {
            throw DuplicateProjectException.byProjectKey(command.projectKey());
        }
        if (existProjectByName.existsByName(command.name())) {
            throw DuplicateProjectException.byName(command.name());
        }
        validateEmployees.validate(new ValidateEmployeesCommand(command.employeeIds()));

        Project project = ProjectMapper.toDomain(
                command
        );
        Project savedProject = saveProject.save(project);

        projectEventPublisher.publishProjectCreated(
                ProjectIntegrationMapper.from(
                        command.employeeId(),
                        savedProject
                )
        );

        return savedProject;
    }
}
