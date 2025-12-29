package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence;

import com.sprintforge.scrum.project.application.port.out.persistence.LoadProjectProgressReportRaw;
import com.sprintforge.scrum.project.application.port.out.persistence.raw.ProjectProgressReportRaw;
import com.sprintforge.scrum.project.application.port.out.persistence.raw.ProjectRawItem;
import com.sprintforge.scrum.project.application.port.out.persistence.raw.StartedSprintRaw;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection.CurrentSprintView;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection.ProjectMemberView;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection.ProjectSprintCountsView;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection.ProjectStoryCountsView;
import com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.repository.ProjectProgressReportJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class ProjectReportRepository implements LoadProjectProgressReportRaw {

    private final ProjectProgressReportJpaRepository jpa;

    @Override
    public ProjectProgressReportRaw load(UUID projectId) {

        List<ProjectStoryCountsView> storyCounts = jpa.storyCounts(projectId);
        List<ProjectSprintCountsView> sprintCounts = jpa.sprintCounts(projectId);
        List<CurrentSprintView> startedSprints = jpa.currentSprints(projectId);
        List<ProjectMemberView> members = jpa.members(projectId);

        Map<UUID, ProjectSprintCountsView> sprintCountsByProject = mapSprintCountsByProject(sprintCounts);
        Map<UUID, List<CurrentSprintView>> startedSprintsByProject = groupStartedSprintsByProject(startedSprints);
        Map<UUID, Set<UUID>> memberIdsByProject = groupMemberIdsByProject(members);

        List<ProjectRawItem> projects = buildProjectRawItems(
                storyCounts,
                sprintCountsByProject,
                startedSprintsByProject,
                memberIdsByProject
        );

        return buildGlobalRawResult(projects);
    }

    private List<ProjectRawItem> buildProjectRawItems(
            List<ProjectStoryCountsView> storyCounts,
            Map<UUID, ProjectSprintCountsView> sprintCountsByProject,
            Map<UUID, List<CurrentSprintView>> startedSprintsByProject,
            Map<UUID, Set<UUID>> memberIdsByProject
    ) {
        List<ProjectRawItem> projects = new ArrayList<>();

        for (ProjectStoryCountsView sc : storyCounts) {
            UUID pid = sc.getProjectId();

            ProjectSprintCountsView sprint = sprintCountsByProject.get(pid);

            long totalSprints = sprint == null ? 0L : sprint.getTotalSprints();
            long startedSprints = sprint == null ? 0L : sprint.getStartedSprints();
            long createdSprints = sprint == null ? 0L : sprint.getCreatedSprints();
            long completedSprints = sprint == null ? 0L : sprint.getCompletedSprints();

            List<StartedSprintRaw> sprintRawList = mapStartedSprints(startedSprintsByProject.get(pid));
            Set<UUID> memberIds = memberIdsByProject.getOrDefault(pid, Set.of());

            projects.add(new ProjectRawItem(
                    pid,
                    sc.getProjectKey(),
                    sc.getProjectName(),
                    sc.getBacklogCount(),
                    sc.getPendingCount(),
                    sc.getInProgressCount(),
                    sc.getCompletedCount(),
                    totalSprints,
                    startedSprints,
                    createdSprints,
                    completedSprints,
                    sprintRawList,
                    memberIds
            ));
        }

        return projects;
    }

    private ProjectProgressReportRaw buildGlobalRawResult(List<ProjectRawItem> projects) {
        long totalProjects = projects.size();

        long totalSprints = 0L;
        long startedSprints = 0L;
        long createdSprints = 0L;
        long completedSprints = 0L;

        for (ProjectRawItem p : projects) {
            totalSprints += p.totalSprints();
            startedSprints += p.startedSprints();
            createdSprints += p.createdSprints();
            completedSprints += p.completedSprints();
        }

        return new ProjectProgressReportRaw(
                totalProjects,
                totalSprints,
                startedSprints,
                createdSprints,
                completedSprints,
                projects
        );
    }

    private Map<UUID, ProjectSprintCountsView> mapSprintCountsByProject(List<ProjectSprintCountsView> sprintCounts) {
        Map<UUID, ProjectSprintCountsView> map = new HashMap<>();
        for (ProjectSprintCountsView v : sprintCounts) {
            map.put(v.getProjectId(), v);
        }
        return map;
    }

    private Map<UUID, List<CurrentSprintView>> groupStartedSprintsByProject(List<CurrentSprintView> startedSprints) {
        Map<UUID, List<CurrentSprintView>> map = new HashMap<>();
        for (CurrentSprintView s : startedSprints) {
            map.computeIfAbsent(s.getProjectId(), ignored -> new ArrayList<>()).add(s);
        }
        return map;
    }

    private Map<UUID, Set<UUID>> groupMemberIdsByProject(List<ProjectMemberView> members) {
        Map<UUID, Set<UUID>> map = new HashMap<>();
        for (ProjectMemberView m : members) {
            map.computeIfAbsent(m.getProjectId(), ignored -> new HashSet<>()).add(m.getEmployeeId());
        }
        return map;
    }

    private List<StartedSprintRaw> mapStartedSprints(List<CurrentSprintView> views) {
        if (views == null || views.isEmpty()) return List.of();

        List<StartedSprintRaw> result = new ArrayList<>();
        for (CurrentSprintView s : views) {
            result.add(new StartedSprintRaw(
                    s.getSprintId(),
                    s.getName(),
                    s.getGoal(),
                    s.getStatus(),
                    s.getStartDate(),
                    s.getEndDate()
            ));
        }
        return result;
    }
}
