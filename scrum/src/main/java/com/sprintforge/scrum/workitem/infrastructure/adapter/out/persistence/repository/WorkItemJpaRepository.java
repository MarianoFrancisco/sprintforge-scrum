package com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.repository;

import com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.entity.WorkItemEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface WorkItemJpaRepository extends
        JpaRepository<WorkItemEntity, UUID>,
        JpaSpecificationExecutor<WorkItemEntity> {
    Optional<WorkItemEntity> findById(UUID uuid);

    List<WorkItemEntity> findAllById(Iterable<UUID> ids);

    @Query("""
             SELECT MAX(w.position)
             FROM WorkItemEntity w
             WHERE w.project.id = :projectId
               AND w.sprint IS NULL
               AND w.isDeleted = false
            """)
    Optional<Integer> findMaxBacklogPosition(UUID projectId);

    @Query("""
             SELECT MAX(w.position)
             FROM WorkItemEntity w
             WHERE w.project.id = :projectId
               AND w.sprint.id = :sprintId
               AND w.boardColumn.id = :columnId
               AND w.isDeleted = false
            """)
    Optional<Integer> findMaxBoardColumnPosition(UUID projectId, UUID sprintId, UUID columnId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                UPDATE WorkItemEntity w
                SET w.position = w.position - 1
                WHERE w.project.id = :projectId
                  AND w.sprint IS NULL
                  AND w.isDeleted = false
                  AND w.position > :deletedPosition
            """)
    void shiftLeftBacklog(
            UUID projectId,
            int deletedPosition
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                UPDATE WorkItemEntity w
                SET w.position = w.position - 1
                WHERE w.project.id = :projectId
                  AND w.sprint.id = :sprintId
                  AND w.boardColumn.id = :columnId
                  AND w.isDeleted = false
                  AND w.position > :deletedPosition
            """)
    void shiftLeftBoardColumn(
            UUID projectId,
            UUID sprintId,
            UUID columnId,
            int deletedPosition
    );

    @Query("""
                SELECT MAX(w.position)
                FROM WorkItemEntity w
                WHERE w.project.id = :projectId
                  AND w.sprint.id = :sprintId
                  AND w.boardColumn.id = :boardColumnId
                  AND w.isDeleted = false
            """)
    Optional<Integer> findMaxPositionInBoardColumn(UUID projectId, UUID sprintId, UUID boardColumnId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                UPDATE WorkItemEntity w
                SET w.position = w.position + 1
                WHERE w.project.id = :projectId
                  AND w.sprint.id = :sprintId
                  AND w.boardColumn.id = :boardColumnId
                  AND w.isDeleted = false
                  AND w.position >= :fromInclusive
                  AND w.position < :toExclusive
            """)
    void shiftUpInBoardColumn(UUID projectId, UUID sprintId, UUID boardColumnId,
                              int fromInclusive, int toExclusive);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                UPDATE WorkItemEntity w
                SET w.position = w.position - 1
                WHERE w.project.id = :projectId
                  AND w.sprint.id = :sprintId
                  AND w.boardColumn.id = :boardColumnId
                  AND w.isDeleted = false
                  AND w.position > :fromExclusive
                  AND w.position <= :toInclusive
            """)
    void shiftDownInBoardColumn(UUID projectId, UUID sprintId, UUID boardColumnId,
                                int fromExclusive, int toInclusive);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                UPDATE WorkItemEntity w
                SET w.position = w.position + :delta
                WHERE w.project.id = :projectId
                  AND w.sprint.id = :sprintId
                  AND w.boardColumn.id = :boardColumnId
                  AND w.isDeleted = false
                  AND w.position >= :fromInclusive
            """)
    void shiftRightInBoardColumn(UUID projectId, UUID sprintId, UUID boardColumnId, int fromInclusive, int delta);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
                WITH ordered AS (
                    SELECT id,
                           ROW_NUMBER() OVER (ORDER BY position) - 1 AS rn
                    FROM work_item
                    WHERE project_id = :projectId
                      AND sprint_id = :sprintId
                      AND column_id = :fromColumnId
                      AND is_deleted = false
                )
                UPDATE work_item w
                SET column_id  = :toColumnId,
                    position   = :startPosition + o.rn,
                    updated_at = CURRENT_TIMESTAMP
                FROM ordered o
                WHERE w.id = o.id
            """, nativeQuery = true)
    void moveAllFromColumnToAnother(UUID projectId, UUID sprintId, UUID fromColumnId, UUID toColumnId, int startPosition);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
                WITH ordered AS (
                    SELECT w.id,
                           ROW_NUMBER() OVER (
                               ORDER BY c.position NULLS LAST, w.position
                           ) - 1 AS rn
                    FROM work_item w
                    LEFT JOIN board_column c ON c.id = w.column_id
                    WHERE w.project_id = :projectId
                      AND w.sprint_id = :sprintId
                      AND w.is_deleted = false
                )
                UPDATE work_item w
                SET sprint_id  = NULL,
                    column_id  = NULL,
                    position   = :startBacklogPosition + o.rn,
                    updated_at = CURRENT_TIMESTAMP
                FROM ordered o
                WHERE w.id = o.id
            """, nativeQuery = true)
    void moveAllFromSprintToBacklog(UUID projectId, UUID sprintId, int startBacklogPosition);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
                WITH ordered AS (
                    SELECT w.id,
                           ROW_NUMBER() OVER (ORDER BY c.position, w.position) - 1 AS rn
                    FROM work_item w
                    JOIN board_column c ON c.id = w.column_id
                    WHERE w.project_id = :projectId
                      AND w.sprint_id = :fromSprintId
                      AND w.is_deleted = false
                      AND c.is_final = false
                )
                UPDATE work_item w
                SET sprint_id  = NULL,
                    column_id  = NULL,
                    position   = :startBacklogPosition + o.rn,
                    updated_at = CURRENT_TIMESTAMP
                FROM ordered o
                WHERE w.id = o.id
            """, nativeQuery = true)
    void moveUnfinishedFromSprintToBacklog(UUID projectId,
                                           UUID fromSprintId,
                                           int startBacklogPosition);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
                WITH ordered AS (
                    SELECT w.id,
                           ROW_NUMBER() OVER (ORDER BY c.position, w.position) - 1 AS rn
                    FROM work_item w
                    JOIN board_column c ON c.id = w.column_id
                    WHERE w.project_id = :projectId
                      AND w.sprint_id = :fromSprintId
                      AND w.is_deleted = false
                      AND c.is_final = false
                )
                UPDATE work_item w
                SET sprint_id  = :toSprintId,
                    column_id  = :toColumnId,
                    position   = :startPosition + o.rn,
                    updated_at = CURRENT_TIMESTAMP
                FROM ordered o
                WHERE w.id = o.id
            """, nativeQuery = true)
    void moveUnfinishedFromSprintToSprint(UUID projectId,
                                          UUID fromSprintId,
                                          UUID toSprintId,
                                          UUID toColumnId,
                                          int startPosition);
}
