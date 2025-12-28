package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.workitem.application.exception.WorkItemNotFoundException;
import com.sprintforge.scrum.workitem.application.port.in.command.UpdateWorkItemTitle;
import com.sprintforge.scrum.workitem.application.port.in.command.UpdateWorkItemTitleCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindWorkItemById;
import com.sprintforge.scrum.workitem.application.port.out.persistence.SaveWorkItem;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateWorkItemTitleImpl implements UpdateWorkItemTitle {

    private final FindWorkItemById findWorkItemById;
    private final SaveWorkItem saveWorkItem;

    @Override
    public WorkItem handle(UpdateWorkItemTitleCommand command) {
        WorkItem workItem = findWorkItemById.findById(command.id()).orElseThrow(
                () -> WorkItemNotFoundException.byId(command.id())
        );
        workItem.updateTitle(command.title());
        WorkItem updatedWorkItem = saveWorkItem.save(workItem);
        return updatedWorkItem;
    }
}
