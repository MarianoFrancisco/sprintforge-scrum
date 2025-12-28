package com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.mapper.ProjectEntityMapper;
import com.sprintforge.scrum.sprint.domain.Sprint;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.entity.SprintEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SprintEntityMapper {
    public Sprint toDomain(SprintEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Sprint(
                entity.getId(),
                entity.getName(),
                entity.getGoal(),
                entity.getStatus(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.isDeleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                ProjectEntityMapper.toDomain(entity.getProject())
        );
    }

    public SprintEntity toEntity(Sprint domain) {
        if (domain == null) {
            return null;
        }

        return SprintEntity.builder()
                .id(domain.getId().value())
                .name(domain.getName().value())
                .goal(domain.getGoal() != null ? domain.getGoal().value() : null)
                .status(domain.getStatus())
                .startDate(domain.getStartDate().value())
                .endDate(domain.getEndDate().value())
                .isDeleted(domain.isDeleted())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .project(ProjectEntityMapper.toEntity(domain.getProject()))
                .build();
    }
}
