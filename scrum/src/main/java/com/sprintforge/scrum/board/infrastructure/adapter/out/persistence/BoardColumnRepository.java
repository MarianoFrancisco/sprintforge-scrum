package com.sprintforge.scrum.board.infrastructure.adapter.out.persistence;

import com.sprintforge.scrum.board.application.port.in.query.GetAllBoardColumnsBySprintIdQuery;
import com.sprintforge.scrum.board.application.port.out.persistence.*;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.entity.BoardColumnEntity;
import com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.mapper.BoardColumnEntityMapper;
import com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.repository.BoardColumnJpaRepository;
import com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.specification.BoardColumnSpecs;
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
public class BoardColumnRepository implements
        ExistsBoardColumnByNameAndIdNot,
        ExistsBoardColumnBySprintIdAndName,
        BoardColumnExistsForSprint,
        FindAllBoardColumnsBySprintId,
        FindBoardColumnById,
        FindBoardColumnBySprintIdAndIsFinalTrue,
        FindLastActiveBoardColumnBySprintId,
        FindLastActiveBoardColumnBySprintIdExcludingId,
        FindDefaultBoardColumnBySprintId,
        FindNextBoardColumnPosition,
        SaveAllBoardColumn,
        SaveBoardColumn,
        ShiftBoardColumnPositionsAfterDeletion,
        ShiftBoardColumnPositions {

    private final BoardColumnJpaRepository boardColumnJpaRepository;

    @Override
    public boolean existsByNameAndIdNot(String name, UUID id) {
        return boardColumnJpaRepository.existsByNameAndIdNot(name, id);
    }

    @Override
    public boolean existsBySprintIdAndName(UUID sprintId, String name) {
        return boardColumnJpaRepository.existsBySprintIdAndName(sprintId, name);
    }

    @Override
    public boolean existsActiveBySprintId(UUID sprintId) {
        return boardColumnJpaRepository.existsBySprintIdAndIsDeletedFalse(sprintId);
    }

    @Override
    public List<BoardColumn> findAllBySprintId(GetAllBoardColumnsBySprintIdQuery query) {
        Specification<BoardColumnEntity> spec = BoardColumnSpecs.withFilters(
                query.projectId(),
                query.searchTerm()
        );

        return boardColumnJpaRepository.findAll(spec)
                .stream()
                .map(BoardColumnEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<BoardColumn> findById(UUID id) {
        return boardColumnJpaRepository.findById(id).map(
                BoardColumnEntityMapper::toDomain
        );
    }

    @Override
    public Optional<BoardColumn> findBySprintIdAndIsFinalTrue(UUID sprintId) {
        return boardColumnJpaRepository.findBySprintIdAndIsFinalTrueAndIsDeletedFalse(sprintId).map(
                BoardColumnEntityMapper::toDomain
        );
    }

    @Override
    public Optional<BoardColumn> findLastActiveBySprintId(UUID sprintId) {
        return boardColumnJpaRepository
                .findFirstBySprintIdAndIsDeletedFalseOrderByPositionDesc(sprintId).map(
                        BoardColumnEntityMapper::toDomain
                );
    }

    @Override
    public Optional<BoardColumn> findLastActiveBySprintIdExcludingId(UUID sprintId, UUID excludeId) {
        return boardColumnJpaRepository
                .findFirstBySprintIdAndIsDeletedFalseAndIdNotOrderByPositionDesc(sprintId, excludeId).map(
                        BoardColumnEntityMapper::toDomain
                );
    }

    @Override
    public Optional<BoardColumn> findDefaultBySprintId(UUID sprintId) {
        return boardColumnJpaRepository
                .findFirstBySprintIdAndIsDeletedFalseOrderByPositionAsc(sprintId)
                .map(BoardColumnEntityMapper::toDomain);
    }

    @Override
    public int findNextPosition(UUID sprintId) {
        return boardColumnJpaRepository.findMaxActivePositionBySprintId(sprintId)
                .orElse(-1) + 1;
    }

    @Override
    public List<BoardColumn> saveAll(List<BoardColumn> boardColumns) {
        List<BoardColumnEntity> entities = boardColumns.stream()
                .map(BoardColumnEntityMapper::toEntity)
                .toList();
        List<BoardColumnEntity> savedEntities = boardColumnJpaRepository.saveAll(entities);
        return savedEntities.stream()
                .map(BoardColumnEntityMapper::toDomain)
                .toList();
    }

    @Override
    public BoardColumn save(BoardColumn boardColumn) {
        BoardColumnEntity entity = BoardColumnEntityMapper.toEntity(boardColumn);
        BoardColumnEntity savedEntity = boardColumnJpaRepository.save(entity);
        return BoardColumnEntityMapper.toDomain(savedEntity);
    }

    @Override
    public void shiftLeft(UUID sprintId, int deletedPosition) {
        boardColumnJpaRepository.shiftLeft(sprintId, deletedPosition);
    }

    @Override
    public void shiftUp(UUID sprintId, int fromInclusive, int toExclusive) {
        boardColumnJpaRepository.shiftUp(sprintId, fromInclusive, toExclusive);

    }

    @Override
    public void shiftDown(UUID sprintId, int fromExclusive, int toInclusive) {
        boardColumnJpaRepository.shiftDown(sprintId, fromExclusive, toInclusive);
    }
}
