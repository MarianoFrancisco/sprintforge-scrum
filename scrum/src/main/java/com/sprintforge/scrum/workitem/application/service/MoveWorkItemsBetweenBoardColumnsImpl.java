package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.board.application.port.in.query.GetBoardColumnById;
import com.sprintforge.scrum.board.application.port.in.query.GetBoardColumnByIdQuery;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.workitem.application.exception.InvalidWorkItemPositionException;
import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemsBetweenBoardColumns;
import com.sprintforge.scrum.workitem.application.port.in.command.MoveWorkItemsBetweenBoardColumnsCommand;
import com.sprintforge.scrum.workitem.application.port.out.persistence.SaveWorkItem;
import com.sprintforge.scrum.workitem.application.port.out.persistence.ShiftWorkItemPositionsAfterDeletionInBoardColumn;
import com.sprintforge.scrum.workitem.application.port.out.persistence.ShiftWorkItemPositionsAfterInsertionInBoardColumn;
import com.sprintforge.scrum.workitem.application.service.internal.OriginKey;
import com.sprintforge.scrum.workitem.application.service.support.WorkItemBulkMoveSupport;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MoveWorkItemsBetweenBoardColumnsImpl implements MoveWorkItemsBetweenBoardColumns {

    private final WorkItemBulkMoveSupport bulkMoveSupport;

    private final GetBoardColumnById getBoardColumnById;

    private final ShiftWorkItemPositionsAfterDeletionInBoardColumn shiftLeftOrigin;
    private final ShiftWorkItemPositionsAfterInsertionInBoardColumn shiftRightDestination;

    private final SaveWorkItem saveWorkItem;

    @Override
    public void handle(MoveWorkItemsBetweenBoardColumnsCommand command) {
        List<UUID> ids = command.ids();
        if (ids == null || ids.isEmpty()) {
            return;
        }

        if (command.targetPosition() < 0) {
            throw InvalidWorkItemPositionException.negative();
        }

        List<WorkItem> items = bulkMoveSupport.loadAndValidate(ids);
        UUID projectId = bulkMoveSupport.projectIdOf(items);

        BoardColumn targetColumn = getBoardColumnById.handle(new GetBoardColumnByIdQuery(command.targetBoardColumnId()));

        Map<OriginKey, List<WorkItem>> byOrigin = items.stream()
                .collect(groupingBy(w -> new OriginKey(
                        w.getSprint().getId().value(),
                        w.getBoardColumn().getId().value()
                )));

        for (Map.Entry<OriginKey, List<WorkItem>> entry : byOrigin.entrySet()) {
            OriginKey key = entry.getKey();

            List<Integer> positionsDesc = entry.getValue().stream()
                    .map(w -> w.getPosition().value())
                    .sorted(Comparator.reverseOrder())
                    .toList();

            for (int pos : positionsDesc) {
                shiftLeftOrigin.shiftLeft(projectId, key.sprintId(), key.boardColumnId(), pos);
            }
        }

        shiftRightDestination.shiftRight(
                projectId,
                command.sprintId(),
                targetColumn.getId().value(),
                command.targetPosition(),
                ids.size()
        );

        Map<UUID, WorkItem> byId = bulkMoveSupport.indexById(items);

        int nextPosition = command.targetPosition();
        List<WorkItem> saved = new ArrayList<>(ids.size());

        for (UUID id : ids) {
            WorkItem w = byId.get(id);
            w.moveToBoardColumn(targetColumn, nextPosition);
            nextPosition++;
            saved.add(saveWorkItem.save(w));
        }
    }
}
