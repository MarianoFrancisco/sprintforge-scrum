package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.workitem.application.exception.WorkItemNotFoundException;
import com.sprintforge.scrum.workitem.application.port.in.command.DeleteWorkItem;
import com.sprintforge.scrum.workitem.application.port.in.command.DeleteWorkItemCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindWorkItemById;
import com.sprintforge.scrum.workitem.application.port.out.persistence.SaveWorkItem;
import com.sprintforge.scrum.workitem.application.port.out.persistence.ShiftWorkItemPositionsAfterDeletionInBacklog;
import com.sprintforge.scrum.workitem.application.port.out.persistence.ShiftWorkItemPositionsAfterDeletionInBoardColumn;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DeleteWorkItemImpl implements DeleteWorkItem {

    private final FindWorkItemById findWorkItemById;
    private final SaveWorkItem saveWorkItem;

    private final ShiftWorkItemPositionsAfterDeletionInBoardColumn shiftInBoard;
    private final ShiftWorkItemPositionsAfterDeletionInBacklog shiftInBacklog;

    @Override
    public void handle(DeleteWorkItemCommand command) {
        WorkItem workItem = findWorkItemById.findById(command.id())
                .orElseThrow(() -> WorkItemNotFoundException.byId(command.id()));

        UUID projectId = workItem.getProject().getId().value();
        int deletedPosition = workItem.getPosition().value();

        workItem.delete();
        saveWorkItem.save(workItem);

        if (workItem.getSprint() == null) {
            shiftInBacklog.shiftLeft(projectId, deletedPosition);
            return;
        }

        shiftInBoard.shiftLeft(
                projectId,
                workItem.getSprint().getId().value(),
                workItem.getBoardColumn().getId().value(),
                deletedPosition
        );
    }
}
