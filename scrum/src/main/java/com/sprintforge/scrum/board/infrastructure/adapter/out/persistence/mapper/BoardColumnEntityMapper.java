package com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.mapper;

import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.entity.BoardColumnEntity;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.persistence.mapper.SprintEntityMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BoardColumnEntityMapper {

    public BoardColumn toDomain(BoardColumnEntity entity) {
        if (entity == null) {
            return null;
        }
        return new BoardColumn(
                entity.getId(),
                entity.getName(),
                entity.getPosition(),
                entity.isFinal(),
                entity.isDeleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                SprintEntityMapper.toDomain(entity.getSprint())
        );
    }

    public BoardColumnEntity toEntity(BoardColumn boardColumn) {
        if (boardColumn == null) {
            return null;
        }
        return BoardColumnEntity.builder()
                .id(boardColumn.getId().value())
                .sprint(SprintEntityMapper.toEntity(boardColumn.getSprint()))
                .name(boardColumn.getName().value())
                .position(boardColumn.getPosition().value())
                .isFinal(boardColumn.isFinal())
                .isDeleted(boardColumn.isDeleted())
                .createdAt(boardColumn.getCreatedAt())
                .updatedAt(boardColumn.getUpdatedAt())
                .build();
    }
}
