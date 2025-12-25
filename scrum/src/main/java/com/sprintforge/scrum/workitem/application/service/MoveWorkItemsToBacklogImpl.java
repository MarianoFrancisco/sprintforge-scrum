package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemsToBacklog;
import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemsToBacklogCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindNextBacklogPosition;
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
public class MoveWorkItemsToBacklogImpl implements MoveWorkItemsToBacklog {

    private final WorkItemBulkMoveSupport bulkMoveSupport;
    private final WorkItemOriginShiftSupport originShiftSupport;

    private final FindNextBacklogPosition findNextBacklogPosition;
    private final SaveWorkItem saveWorkItem;

    @Override
    public void handle(MoveWorkItemsToBacklogCommand command) {
        List<UUID> ids = command.ids();
        if (ids == null || ids.isEmpty()) {
            return;
        }

        List<WorkItem> items = bulkMoveSupport.loadAndValidate(ids);
        UUID projectId = bulkMoveSupport.projectIdOf(items);

        int nextBacklogPosition = findNextBacklogPosition.findNextPosition(projectId);

        originShiftSupport.shiftOriginsLeft(projectId, items);

        moveAndSaveInInputOrder(ids, items, nextBacklogPosition);
    }

    private void moveAndSaveInInputOrder(List<UUID> ids, List<WorkItem> items, int startBacklogPosition) {
        Map<UUID, WorkItem> byId = bulkMoveSupport.indexById(items);

        int nextPosition = startBacklogPosition;

        List<WorkItem> saved = new ArrayList<>(ids.size());
        for (UUID id : ids) {
            WorkItem workItem = byId.get(id);
            workItem.moveToBacklog(nextPosition);
            nextPosition++;
            saved.add(saveWorkItem.save(workItem));
        }
    }
}
