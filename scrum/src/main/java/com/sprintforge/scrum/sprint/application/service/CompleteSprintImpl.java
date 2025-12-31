package com.sprintforge.scrum.sprint.application.service;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.common.application.service.support.EmployeeQuerySupport;
import com.sprintforge.scrum.sprint.application.exception.SprintNotFoundException;
import com.sprintforge.scrum.sprint.application.mapper.SprintIntegrationMapper;
import com.sprintforge.scrum.sprint.application.port.in.command.CompleteSprint;
import com.sprintforge.scrum.sprint.application.port.in.command.CompleteSprintCommand;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintEventPublisher;
import com.sprintforge.scrum.sprint.application.port.out.event.notification.EmailSprintCreatedIntegrationEvent;
import com.sprintforge.scrum.sprint.application.port.out.event.notification.NotificationEventPublisher;
import com.sprintforge.scrum.sprint.application.port.out.persistence.FindSprintById;
import com.sprintforge.scrum.sprint.application.port.out.persistence.SaveSprint;
import com.sprintforge.scrum.sprint.domain.Sprint;
import com.sprintforge.scrum.workitem.application.port.in.command.ReassignWorkItemsAfterSprintCompletion;
import com.sprintforge.scrum.workitem.application.port.in.command.ReassignWorkItemsAfterSprintCompletionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CompleteSprintImpl implements CompleteSprint {

    private final FindSprintById findSprintById;
    private final SaveSprint saveSprint;
    private final EmployeeQuerySupport employeeQuerySupport;
    private final SprintEventPublisher sprintEventPublisher;
    private final NotificationEventPublisher notificationEventPublisher;

    private final ReassignWorkItemsAfterSprintCompletion reassignWorkItemsAfterSprintCompletion;

    @Override
    public Sprint handle(CompleteSprintCommand command) {
        Sprint sprint = findSprintById.findById(command.id()).orElseThrow(
                () -> SprintNotFoundException.byId(command.id())
        );

        UUID projectId = sprint.getProject().getId().value();

        reassignWorkItemsAfterSprintCompletion.handle(
                new ReassignWorkItemsAfterSprintCompletionCommand(
                        command.employeeId(),
                        projectId,
                        sprint.getId().value(),
                        command.targetSprintId()
                )
        );

        sprint.complete();
        Sprint savedSprint = saveSprint.save(sprint);

        EmployeeResult employee =
                employeeQuerySupport.getEmployee(command.employeeId());

        sprintEventPublisher.publishSprintCompleted(
                SprintIntegrationMapper.sprintCompleted(
                        employee,
                        savedSprint
                )
        );

        notificationEventPublisher.publishEmailSprintCreated(
                new EmailSprintCreatedIntegrationEvent(
                        employee.email(),
                        employee.fullName(),
                        sprint.getName().value()
                )
        );
        return savedSprint;
    }
}
