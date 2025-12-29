package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.repository;

import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.entity.ProjectEntity;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection.CurrentSprintView;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection.ProjectMemberView;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection.ProjectSprintCountsView;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection.ProjectStoryCountsView;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

@NullMarked
public interface ProjectProgressReportJpaRepository extends
        JpaRepository<ProjectEntity, UUID> {

    @Query(value = """
            SELECT
                p.id          AS projectId,
                p.project_key AS projectKey,
                p.name        AS projectName,
            
                COALESCE(SUM(CASE
                    WHEN wi.is_deleted = false
                     AND wi.sprint_id IS NULL
                     AND wi.column_id IS NULL
                    THEN 1 ELSE 0 END), 0) AS backlogCount,
            
                COALESCE(SUM(CASE
                    WHEN wi.is_deleted = false
                     AND wi.column_id IS NOT NULL
                     AND bc.is_deleted = false
                     AND bc.is_final = false
                     AND bc.position = 0
                    THEN 1 ELSE 0 END), 0) AS pendingCount,
            
                COALESCE(SUM(CASE
                    WHEN wi.is_deleted = false
                     AND wi.column_id IS NOT NULL
                     AND bc.is_deleted = false
                     AND bc.is_final = false
                     AND bc.position <> 0
                    THEN 1 ELSE 0 END), 0) AS inProgressCount,
            
                COALESCE(SUM(CASE
                    WHEN wi.is_deleted = false
                     AND wi.column_id IS NOT NULL
                     AND bc.is_deleted = false
                     AND bc.is_final = true
                    THEN 1 ELSE 0 END), 0) AS completedCount
            
            FROM project p
            LEFT JOIN work_item wi ON wi.project_id = p.id
            LEFT JOIN board_column bc ON bc.id = wi.column_id
            WHERE p.is_deleted = false
              AND p.id = COALESCE(?1, p.id)
            GROUP BY p.id, p.project_key, p.name
            ORDER BY p.project_key
            """, nativeQuery = true)
    List<ProjectStoryCountsView> storyCounts(UUID projectId);

    @Query(value = """
            SELECT
                s.project_id AS projectId,
            
                COUNT(*) AS totalSprints,
            
                COALESCE(SUM(CASE WHEN s.status = 'STARTED' THEN 1 ELSE 0 END), 0) AS startedSprints,
                COALESCE(SUM(CASE WHEN s.status = 'CREATED' THEN 1 ELSE 0 END), 0) AS createdSprints,
                COALESCE(SUM(CASE WHEN s.status = 'COMPLETED' THEN 1 ELSE 0 END), 0) AS completedSprints
            
            FROM sprint s
            WHERE s.is_deleted = false
              AND s.project_id = COALESCE(?1, s.project_id)
            GROUP BY s.project_id
            """, nativeQuery = true)
    List<ProjectSprintCountsView> sprintCounts(UUID projectId);

    @Query(value = """
            SELECT
                s.project_id AS projectId,
                s.id         AS sprintId,
                s.name       AS name,
                s.goal       AS goal,
                s.status     AS status,
                s.start_date AS startDate,
                s.end_date   AS endDate
            FROM sprint s
            WHERE s.is_deleted = false
              AND s.status = 'STARTED'
              AND s.project_id = COALESCE(?1, s.project_id)
            ORDER BY s.start_date DESC
            """, nativeQuery = true)
    List<CurrentSprintView> currentSprints(UUID projectId);

    @Query(value = """
            SELECT
                pe.project_id  AS projectId,
                pe.employee_id AS employeeId
            FROM project_employee pe
            WHERE pe.project_id = COALESCE(?1, pe.project_id)
            """, nativeQuery = true)
    List<ProjectMemberView> members(UUID projectId);
}
