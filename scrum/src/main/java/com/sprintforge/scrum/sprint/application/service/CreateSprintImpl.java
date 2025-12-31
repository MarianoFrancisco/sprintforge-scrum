package com.sprintforge.scrum.sprint.application.service;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.board.application.port.in.command.InitializeBoardColumns;
import com.sprintforge.scrum.board.application.port.in.command.InitializeBoardColumnsCommand;
import com.sprintforge.scrum.common.application.service.support.EmployeeQuerySupport;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectById;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectByIdQuery;
import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.sprint.application.mapper.SprintIntegrationMapper;
import com.sprintforge.scrum.sprint.application.mapper.SprintMapper;
import com.sprintforge.scrum.sprint.application.port.in.command.CreateSprint;
import com.sprintforge.scrum.sprint.application.port.in.command.CreateSprintCommand;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintEventPublisher;
import com.sprintforge.scrum.sprint.application.port.out.event.notification.EmailSprintCreatedIntegrationEvent;
import com.sprintforge.scrum.sprint.application.port.out.event.notification.NotificationEventPublisher;
import com.sprintforge.scrum.sprint.application.port.out.persistence.SaveSprint;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateSprintImpl implements CreateSprint {

    private final GetProjectById getProjectById;
    private final SaveSprint saveSprint;
    private final InitializeBoardColumns initializeBoardColumns;
    private final EmployeeQuerySupport employeeQuerySupport;
    private final SprintEventPublisher sprintEventPublisher;
    private final NotificationEventPublisher notificationEventPublisher;

    @Override
    public Sprint handle(CreateSprintCommand command) {
        Project project = getProjectById.handle(
                new GetProjectByIdQuery(command.projectId())
        );

        Sprint sprint = SprintMapper.toDomain(command, project);
        Sprint savedSprint = saveSprint.save(sprint);
        initializeBoardColumns.handle(
                new InitializeBoardColumnsCommand(savedSprint.getId().value())
        );

        EmployeeResult employee =
                employeeQuerySupport.getEmployee(command.employeeId());

        sprintEventPublisher.publishSprintCreated(
                SprintIntegrationMapper.sprintCreated(
                        employee,
                        savedSprint
                )
        );
        this.processNotification(sprint);
        return savedSprint;
    }

    public void processNotification(Sprint sprint) {
        sprint.getProject().getAssignments().stream().map(
                assignment -> employeeQuerySupport.getEmployee(assignment.getEmployeeId().value())
        ).forEach(
                employee -> sendEmailSprintCreatedNotification(employee, sprint)
        );
    }

    public void sendEmailSprintCreatedNotification(EmployeeResult employee, Sprint sprint) {
        notificationEventPublisher.publishEmailSprintCreated(
                new EmailSprintCreatedIntegrationEvent(
                        employee.email(),
                        employee.fullName(),
                        sprint.getName().value()
                )
        );
    }
}
