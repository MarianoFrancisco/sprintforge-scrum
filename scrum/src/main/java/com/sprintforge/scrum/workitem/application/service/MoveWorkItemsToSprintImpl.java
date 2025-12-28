package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.board.application.port.in.query.GetDefaultBoardColumnBySprint;
import com.sprintforge.scrum.board.application.port.in.query.GetDefaultBoardColumnBySprintQuery;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintById;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintByIdQuery;
import com.sprintforge.scrum.sprint.domain.Sprint;
import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemsToSprint;
import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemsToSprintCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindNextBoardColumnPosition;
import com.sprintforge.scrum.workitem.application.port.out.persistence.SaveWorkItem;
import com.sprintforge.scrum.workitem.application.service.support.WorkItemBulkMoveSupport;
import com.sprintforge.scrum.workitem.application.service.support.WorkItemOriginShiftSupport;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MoveWorkItemsToSprintImpl implements MoveWorkItemsToSprint {

    private final WorkItemBulkMoveSupport bulkMoveSupport;
    private final WorkItemOriginShiftSupport originShiftSupport;

    private final GetSprintById getSprintById;
    private final GetDefaultBoardColumnBySprint getDefaultBoardColumnBySprint;

    private final FindNextBoardColumnPosition findNextBoardColumnPosition;

    private final SaveWorkItem saveWorkItem;

    @Override
    public void handle(MoveWorkItemsToSprintCommand command) {
        List<UUID> ids = command.ids();
        if (ids == null || ids.isEmpty()) {
            return;
        }

        List<WorkItem> items = bulkMoveSupport.loadAndValidate(ids);
        UUID projectId = bulkMoveSupport.projectIdOf(items);

        Sprint sprint = getSprintById.handle(new GetSprintByIdQuery(command.sprintId()));

        BoardColumn defaultColumn = getDefaultBoardColumnBySprint.handle(
                new GetDefaultBoardColumnBySprintQuery(command.sprintId())
        );

        originShiftSupport.shiftOriginsLeft(projectId, items);

        int nextPosition = findNextBoardColumnPosition.findNextPosition(
                projectId,
                command.sprintId(),
                defaultColumn.getId().value()
        );

        moveAndSaveInInputOrder(ids, items, sprint, defaultColumn, nextPosition);
    }

    private void moveAndSaveInInputOrder(
            List<UUID> ids,
            List<WorkItem> items,
            Sprint sprint,
            BoardColumn defaultColumn,
            int startPosition
    ) {
        Map<UUID, WorkItem> byId = bulkMoveSupport.indexById(items);

        int nextPosition = startPosition;

        List<WorkItem> saved = new ArrayList<>(ids.size());
        for (UUID id : ids) {
            WorkItem workItem = byId.get(id);
            workItem.moveToSprint(sprint, defaultColumn, nextPosition);
            nextPosition++;
            saved.add(saveWorkItem.save(workItem));
        }
    }
}
