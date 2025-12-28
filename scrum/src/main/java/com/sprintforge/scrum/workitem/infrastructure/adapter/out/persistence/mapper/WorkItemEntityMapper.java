package com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.mapper.BoardColumnEntityMapper;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.mapper.ProjectEntityMapper;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.mapper.SprintEntityMapper;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.entity.WorkItemEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WorkItemEntityMapper {
    public WorkItem toDomain(WorkItemEntity entity) {
        if (entity == null) {
            return null;
        }
        return new WorkItem(
                entity.getId(),
                entity.getPosition(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getAcceptanceCriteria(),
                entity.getStoryPoints(),
                entity.getPriority(),
                entity.getDeveloperId(),
                entity.getProductOwnerId(),
                entity.isDeleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                ProjectEntityMapper.toDomain(entity.getProject()),
                SprintEntityMapper.toDomain(entity.getSprint()),
                BoardColumnEntityMapper.toDomain(entity.getBoardColumn())
        );
    }

    public WorkItemEntity toEntity(WorkItem workItem) {
        if (workItem == null) {
            return null;
        }
        return WorkItemEntity.builder()
                .id(workItem.getId().value())
                .position(workItem.getPosition().value())
                .title(workItem.getTitle().value())
                .description(workItem.getDescription().value())
                .acceptanceCriteria(workItem.getAcceptanceCriteria().value())
                .storyPoints(workItem.getStoryPoints() != null ? workItem.getStoryPoints().value() : null)
                .priority(workItem.getPriority().value())
                .developerId(workItem.getDeveloperId() != null ? workItem.getDeveloperId().value() : null)
                .productOwnerId(workItem.getProductOwnerId() != null ? workItem.getProductOwnerId().value() : null)
                .isDeleted(workItem.isDeleted())
                .createdAt(workItem.getCreatedAt())
                .updatedAt(workItem.getUpdatedAt())
                .project(ProjectEntityMapper.toEntity(workItem.getProject()))
                .sprint(SprintEntityMapper.toEntity(workItem.getSprint()))
                .boardColumn(BoardColumnEntityMapper.toEntity(workItem.getBoardColumn()))
                .build();
    }
}
