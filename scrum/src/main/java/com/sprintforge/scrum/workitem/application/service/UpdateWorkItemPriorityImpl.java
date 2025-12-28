package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.workitem.application.exception.WorkItemNotFoundException;
import com.sprintforge.scrum.workitem.application.port.in.command.UpdateWorkItemPriority;
import com.sprintforge.scrum.workitem.application.port.in.command.UpdateWorkItemPriorityCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindWorkItemById;
import com.sprintforge.scrum.workitem.application.port.out.persistence.SaveWorkItem;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateWorkItemPriorityImpl implements UpdateWorkItemPriority {

    private final FindWorkItemById findWorkItemById;
    private final SaveWorkItem saveWorkItem;

    @Override
    public WorkItem handle(UpdateWorkItemPriorityCommand command) {
        WorkItem workItem = findWorkItemById.findById(command.id()).orElseThrow(
                () -> WorkItemNotFoundException.byId(command.id())
        );
        workItem.updatePriority(command.priority());
        WorkItem updatedWorkItem = saveWorkItem.save(workItem);
        return updatedWorkItem;
    }
}
