package com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.repository;

import com.sprintforge.scrum.board.infrastructure.adapter.out.persistence.entity.BoardColumnEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface BoardColumnJpaRepository extends
        JpaRepository<BoardColumnEntity, UUID>,
        JpaSpecificationExecutor<BoardColumnEntity> {
    boolean existsByNameAndIdNot(String name, UUID id);

    boolean existsBySprintIdAndName(UUID sprintId, String name);

    boolean existsBySprintIdAndIsDeletedFalse(UUID sprintId);

    Optional<BoardColumnEntity> findById(UUID id);

    Optional<BoardColumnEntity> findBySprintIdAndIsFinalTrueAndIsDeletedFalse(UUID sprintId);

    Optional<BoardColumnEntity> findFirstBySprintIdAndIsDeletedFalseOrderByPositionAsc(UUID sprintId);

    Optional<BoardColumnEntity> findFirstBySprintIdAndIsDeletedFalseOrderByPositionDesc(UUID sprintId);

    Optional<BoardColumnEntity> findFirstBySprintIdAndIsDeletedFalseAndIdNotOrderByPositionDesc(UUID sprintId, UUID excludeId);

    @Query("""
                SELECT MAX(b.position)
                FROM BoardColumnEntity b
                WHERE b.sprint.id = :sprintId
                  AND b.isDeleted = false
            """)
    Optional<Integer> findMaxActivePositionBySprintId(UUID sprintId);

    @Modifying
    @Query("""
                UPDATE BoardColumnEntity c
                SET c.position = c.position - 1
                WHERE c.sprint.id = :sprintId
                  AND c.isDeleted = false
                  AND c.position > :deletedPosition
            """)
    int shiftLeft(UUID sprintId, int deletedPosition);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                UPDATE BoardColumnEntity b
                SET b.position = b.position + 1
                WHERE b.sprint.id = :sprintId
                  AND b.isDeleted = false
                  AND b.position >= :fromInclusive
                  AND b.position < :toExclusive
            """)
    int shiftUp(UUID sprintId, int fromInclusive, int toExclusive);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                UPDATE BoardColumnEntity b
                SET b.position = b.position - 1
                WHERE b.sprint.id = :sprintId
                  AND b.isDeleted = false
                  AND b.position > :fromExclusive
                  AND b.position <= :toInclusive
            """)
    int shiftDown(UUID sprintId, int fromExclusive, int toInclusive);
}
