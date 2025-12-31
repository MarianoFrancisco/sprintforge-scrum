package com.sprintforge.scrum.sprint.application.service;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.common.application.service.support.EmployeeQuerySupport;
import com.sprintforge.scrum.sprint.application.exception.SprintNotFoundException;
import com.sprintforge.scrum.sprint.application.mapper.SprintIntegrationMapper;
import com.sprintforge.scrum.sprint.application.port.in.command.DeleteSprint;
import com.sprintforge.scrum.sprint.application.port.in.command.DeleteSprintCommand;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintEventPublisher;
import com.sprintforge.scrum.sprint.application.port.out.persistence.FindSprintById;
import com.sprintforge.scrum.sprint.application.port.out.persistence.SaveSprint;
import com.sprintforge.scrum.sprint.domain.Sprint;
import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemsToBacklogAfterSprintDeletion;
import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemsToBacklogAfterSprintDeletionCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DeleteSprintImpl implements DeleteSprint {

    private final FindSprintById findSprintById;
    private final SaveSprint saveSprint;
    private final EmployeeQuerySupport employeeQuerySupport;
    private final SprintEventPublisher sprintEventPublisher;

    private final MoveWorkItemsToBacklogAfterSprintDeletion moveWorkItemsToBacklogAfterSprintDeletion;

    @Override
    public void handle(DeleteSprintCommand command) {
        Sprint sprint = findSprintById.findById(command.id()).orElseThrow(
                () -> SprintNotFoundException.byId(command.id())
        );

        UUID projectId = sprint.getProject().getId().value();

        moveWorkItemsToBacklogAfterSprintDeletion.handle(
                new MoveWorkItemsToBacklogAfterSprintDeletionCommand(
                        command.employeeId(),
                        projectId,
                        sprint.getId().value()
                )
        );

        sprint.delete();
        Sprint deletedSprint = saveSprint.save(sprint);

        EmployeeResult employee =
                employeeQuerySupport.getEmployee(command.employeeId());

        sprintEventPublisher.publishSprintDeleted(
                SprintIntegrationMapper.sprintDeleted(
                        employee,
                        deletedSprint
                )
        );
    }
}
