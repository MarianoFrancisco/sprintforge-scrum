package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.workitem.application.exception.InvalidWorkItemPositionException;
import com.sprintforge.scrum.workitem.application.exception.WorkItemNotFoundException;
import com.sprintforge.scrum.workitem.application.exception.WorkItemNotInBoardColumnException;
import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemInBoardColumn;
import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemInBoardColumnCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindMaxWorkItemPositionInBoardColumn;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindWorkItemById;
import com.sprintforge.scrum.workitem.application.port.out.persistence.SaveWorkItem;
import com.sprintforge.scrum.workitem.application.port.out.persistence.ShiftWorkItemPositionsInBoardColumn;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MoveWorkItemInBoardColumnImpl implements MoveWorkItemInBoardColumn {

    private final FindWorkItemById findWorkItemById;
    private final FindMaxWorkItemPositionInBoardColumn findMaxWorkItemPositionInBoardColumn;
    private final ShiftWorkItemPositionsInBoardColumn shiftWorkItemPositionsInBoardColumn;
    private final SaveWorkItem saveWorkItem;

    @Override
    public WorkItem handle(MoveWorkItemInBoardColumnCommand command) {
        WorkItem workItem = findWorkItemById.findById(command.id())
                .orElseThrow(() -> WorkItemNotFoundException.byId(command.id()));

        if (workItem.getSprint() == null) {
            throw WorkItemNotInBoardColumnException.byId(command.id());
        }

        int oldPosition = workItem.getPosition().value();
        int newPosition = command.newPosition();

        if (newPosition < 0) {
            throw InvalidWorkItemPositionException.negative();
        }
        if (newPosition == oldPosition) {
            return workItem;
        }

        UUID projectId = workItem.getProject().getId().value();
        UUID sprintId = workItem.getSprint().getId().value();
        UUID columnId = workItem.getBoardColumn().getId().value();

        int maxPosition = findMaxWorkItemPositionInBoardColumn.findMaxPosition(projectId, sprintId, columnId)
                .orElse(0);

        if (newPosition > maxPosition) {
            throw InvalidWorkItemPositionException.outOfRange(newPosition, maxPosition);
        }

        if (newPosition > oldPosition) {
            shiftWorkItemPositionsInBoardColumn.shiftDown(projectId, sprintId, columnId, oldPosition, newPosition);
        } else {
            shiftWorkItemPositionsInBoardColumn.shiftUp(projectId, sprintId, columnId, newPosition, oldPosition);
        }

        workItem.updatePosition(newPosition);
        return saveWorkItem.save(workItem);
    }
}
