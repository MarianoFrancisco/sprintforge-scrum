package com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence;

import com.sprintforge.scrum.workitem.application.port.in.query.GetAllWorkItemsQuery;
import com.sprintforge.scrum.workitem.application.port.out.persistence.*;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.entity.WorkItemEntity;
import com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.mapper.WorkItemEntityMapper;
import com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.repository.WorkItemJpaRepository;
import com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.specification.WorkItemSpecs;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NullMarked
@Repository
@RequiredArgsConstructor
public class WorkItemRepository implements
        FindAllWorkItems,
        FindWorkItemById,
        FindWorkItemsByIds,
        FindNextBacklogPosition,
        FindNextBoardColumnPosition,
        FindMaxWorkItemPositionInBoardColumn,
        SaveWorkItem,
        ShiftWorkItemPositionsAfterDeletionInBacklog,
        ShiftWorkItemPositionsAfterDeletionInBoardColumn,
        ShiftWorkItemPositionsInBoardColumn,
        ShiftWorkItemPositionsAfterInsertionInBoardColumn,
        MoveWorkItemsFromBoardColumnToAnother,
        MoveWorkItemsFromSprintToBacklog,
        MoveUnfinishedWorkItemsFromSprintToBacklog,
        MoveUnfinishedWorkItemsFromSprintToSprint {
    private final WorkItemJpaRepository workItemJpaRepository;

    @Override
    public List<WorkItem> findAll(GetAllWorkItemsQuery query) {
        Specification<WorkItemEntity> spec = WorkItemSpecs.withFilters(
                query.projectId(),
                query.sprintId(),
                query.boardColumnId(),
                query.priority(),
                query.developerId(),
                query.productOwnerId(),
                query.searchTerm()
        );

        return workItemJpaRepository.findAll(spec)
                .stream()
                .map(WorkItemEntityMapper::toDomain)
                .toList();
    }

    @Override
    public int findNextPosition(UUID projectId) {
        return workItemJpaRepository.findMaxBacklogPosition(projectId)
                .map(max -> max + 1)
                .orElse(0);
    }

    @Override
    public int findNextPosition(UUID projectId, UUID sprintId, UUID columnId) {
        return workItemJpaRepository.findMaxBoardColumnPosition(projectId, sprintId, columnId)
                .map(max -> max + 1)
                .orElse(0);
    }

    @Override
    public Optional<WorkItem> findById(UUID id) {
        return workItemJpaRepository.findById(id).map(
                WorkItemEntityMapper::toDomain
        );
    }

    @Override
    public List<WorkItem> findAllByIds(List<UUID> ids) {
        return workItemJpaRepository.findAllById(ids).stream()
                .map(WorkItemEntityMapper::toDomain)
                .toList();
    }

    @Override
    public WorkItem save(WorkItem workItem) {
        WorkItemEntity entity = WorkItemEntityMapper.toEntity(workItem);
        WorkItemEntity savedEntity = workItemJpaRepository.save(entity);
        return WorkItemEntityMapper.toDomain(savedEntity);
    }

    @Override
    public void shiftLeft(UUID projectId, int deletedPosition) {
        workItemJpaRepository.shiftLeftBacklog(projectId, deletedPosition);
    }

    @Override
    public void shiftLeft(UUID projectId, UUID sprintId, UUID columnId, int deletedPosition) {
        workItemJpaRepository.shiftLeftBoardColumn(projectId, sprintId, columnId, deletedPosition);
    }

    @Override
    public Optional<Integer> findMaxPosition(UUID projectId, UUID sprintId, UUID boardColumnId) {
        return workItemJpaRepository.findMaxPositionInBoardColumn(projectId, sprintId, boardColumnId);
    }

    @Override
    public void shiftUp(UUID projectId, UUID sprintId, UUID boardColumnId, int fromInclusive, int toExclusive) {
        workItemJpaRepository.shiftUpInBoardColumn(projectId, sprintId, boardColumnId, fromInclusive, toExclusive);
    }

    @Override
    public void shiftDown(UUID projectId, UUID sprintId, UUID boardColumnId, int fromExclusive, int toInclusive) {
        workItemJpaRepository.shiftDownInBoardColumn(projectId, sprintId, boardColumnId, fromExclusive, toInclusive);
    }

    @Override
    public void shiftRight(UUID projectId, UUID sprintId, UUID boardColumnId, int fromInclusive, int delta) {
        workItemJpaRepository.shiftRightInBoardColumn(projectId, sprintId, boardColumnId, fromInclusive, delta);
    }

    @Override
    public void moveAll(UUID projectId, UUID sprintId, UUID fromBoardColumnId, UUID toBoardColumnId, int startPositionInTarget) {
        workItemJpaRepository.moveAllFromColumnToAnother(projectId, sprintId, fromBoardColumnId, toBoardColumnId, startPositionInTarget);
    }

    @Override
    public void moveAll(UUID projectId, UUID sprintId, int startBacklogPosition) {
        workItemJpaRepository.moveAllFromSprintToBacklog(projectId, sprintId, startBacklogPosition);
    }
}
