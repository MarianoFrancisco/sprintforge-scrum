package com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.repository;

import com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.entity.WorkItemEntity;
import com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.projection.EmployeeProductivityReportView;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@NullMarked
public interface WorkItemReportJpaRepository extends
        Repository<WorkItemEntity, UUID> {
    @Query(value = """
            SELECT
                wi.developer_id AS employeeId,
                COUNT(*)        AS workedStories,
                COUNT(*) FILTER (WHERE bc.is_final = TRUE)  AS completedStories,
                COUNT(*) FILTER (WHERE bc.is_final = FALSE) AS pendingStories,
                COALESCE(SUM(wi.story_points) FILTER (WHERE bc.is_final = TRUE), 0) AS deliveredStoryPoints
            FROM work_item wi
            JOIN board_column bc ON bc.id = wi.column_id
            WHERE wi.is_deleted = FALSE
              AND wi.developer_id IS NOT NULL
              AND wi.updated_at >= :fromInstant
              AND wi.updated_at <  :toInstant
              AND (:employeeId IS NULL OR wi.developer_id = :employeeId)
            GROUP BY wi.developer_id
            ORDER BY deliveredStoryPoints DESC, completedStories DESC
            """, nativeQuery = true)
    List<EmployeeProductivityReportView> findEmployeeProductivityReport(
            Instant fromInstant,
            Instant toInstant,
            UUID employeeId
    );
}
