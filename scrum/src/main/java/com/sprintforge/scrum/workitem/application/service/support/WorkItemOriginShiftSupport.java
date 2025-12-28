package com.sprintforge.scrum.workitem.application.service.support;

import com.sprintforge.scrum.workitem.application.exception.WorkItemNotInBoardColumnException;
import com.sprintforge.scrum.workitem.application.port.out.persistence.ShiftWorkItemPositionsAfterDeletionInBacklog;
import com.sprintforge.scrum.workitem.application.port.out.persistence.ShiftWorkItemPositionsAfterDeletionInBoardColumn;
import com.sprintforge.scrum.workitem.application.service.internal.OriginKey;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.*;

@Component
@RequiredArgsConstructor
public class WorkItemOriginShiftSupport {

    private final ShiftWorkItemPositionsAfterDeletionInBacklog shiftInBacklog;
    private final ShiftWorkItemPositionsAfterDeletionInBoardColumn shiftInBoard;

    public void shiftOriginsLeft(UUID projectId, List<WorkItem> items) {
        shiftBacklogOriginsLeft(projectId, items);
        shiftBoardOriginsLeft(projectId, items);
    }

    private void shiftBacklogOriginsLeft(UUID projectId, List<WorkItem> items) {
        List<Integer> backlogPositionsDesc = items.stream()
                .filter(w -> w.getSprint() == null)
                .map(w -> w.getPosition().value())
                .sorted(Comparator.reverseOrder())
                .toList();

        for (int pos : backlogPositionsDesc) {
            shiftInBacklog.shiftLeft(projectId, pos);
        }
    }

    private void shiftBoardOriginsLeft(UUID projectId, List<WorkItem> items) {
        Map<OriginKey, List<WorkItem>> byOrigin = items.stream()
                .filter(w -> w.getSprint() != null)
                .peek(w -> {
                    if (w.getBoardColumn() == null) {
                        throw WorkItemNotInBoardColumnException.byId(w.getId().value());
                    }
                })
                .collect(groupingBy(w -> new OriginKey(
                        w.getSprint().getId().value(),
                        w.getBoardColumn().getId().value()
                )));

        for (Map.Entry<OriginKey, List<WorkItem>> entry : byOrigin.entrySet()) {
            OriginKey key = entry.getKey();
            List<WorkItem> inSameColumn = entry.getValue();

            List<Integer> positionsDesc = inSameColumn.stream()
                    .map(w -> w.getPosition().value())
                    .sorted(Comparator.reverseOrder())
                    .toList();

            for (int pos : positionsDesc) {
                shiftInBoard.shiftLeft(projectId, key.sprintId(), key.boardColumnId(), pos);
            }
        }
    }
}
