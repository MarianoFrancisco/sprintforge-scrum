package com.sprintforge.scrum.project.application.service;

import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployees;
import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployeesCommand;
import com.sprintforge.scrum.project.application.exception.ProjectNotFoundException;
import com.sprintforge.scrum.project.application.port.in.command.AssignProjectEmployees;
import com.sprintforge.scrum.project.application.port.in.command.AssignProjectEmployeesCommand;
import com.sprintforge.scrum.project.application.port.out.persistence.FindProjectById;
import com.sprintforge.scrum.project.application.port.out.persistence.SaveProject;
import com.sprintforge.scrum.project.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AssignProjectEmployeesImpl implements AssignProjectEmployees {

    private final ValidateEmployees validateEmployees;
    private final FindProjectById findProjectById;
    private final SaveProject saveProject;

    @Override
    public Project handle(AssignProjectEmployeesCommand command) {
        validateEmployees.validate(new ValidateEmployeesCommand(command.employeeIds()));
        Project project = findProjectById.findById(command.id()).orElseThrow(
                () -> ProjectNotFoundException.byId(command.id())
        );
        project.assignEmployees(command.employeeIds());
        Project assignedProject = saveProject.save(project);
        return assignedProject;
    }
}
