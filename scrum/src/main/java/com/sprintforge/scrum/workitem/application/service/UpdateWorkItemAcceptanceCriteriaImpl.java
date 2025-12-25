package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.workitem.application.exception.WorkItemNotFoundException;
import com.sprintforge.scrum.workitem.application.port.in.command.UpdateWorkItemAcceptanceCriteria;
import com.sprintforge.scrum.workitem.application.port.in.command.UpdateWorkItemAcceptanceCriteriaCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindWorkItemById;
import com.sprintforge.scrum.workitem.application.port.out.persistence.SaveWorkItem;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateWorkItemAcceptanceCriteriaImpl implements UpdateWorkItemAcceptanceCriteria {

    private final FindWorkItemById findWorkItemById;
    private final SaveWorkItem saveWorkItem;

    @Override
    public WorkItem handle(UpdateWorkItemAcceptanceCriteriaCommand command) {
        WorkItem workItem = findWorkItemById.findById(command.id()).orElseThrow(
                () -> WorkItemNotFoundException.byId(command.id())
        );
        workItem.updateAcceptanceCriteria(command.acceptanceCriteria());
        WorkItem updatedWorkItem = saveWorkItem.save(workItem);
        return updatedWorkItem;
    }
}
