package com.sprintforge.scrum.project.application.mapper;

import com.sprintforge.scrum.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.project.application.port.out.persistence.raw.ProjectProgressReportRaw;
import com.sprintforge.scrum.project.application.port.out.persistence.raw.ProjectRawItem;
import com.sprintforge.scrum.project.application.port.out.persistence.raw.StartedSprintRaw;
import com.sprintforge.scrum.project.application.port.result.ProjectProgressItem;
import com.sprintforge.scrum.project.application.port.result.ProjectProgressReportResult;
import com.sprintforge.scrum.project.application.port.result.SprintCurrent;
import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class ProjectProgressReportMapper {

    public ProjectProgressReportResult toResult(
            ProjectProgressReportRaw raw,
            Map<UUID, EmployeeResult> employeeById
    ) {
        List<ProjectProgressItem> projects = mapProjects(raw.projects(), employeeById);

        return new ProjectProgressReportResult(
                raw.totalProjects(),
                raw.totalSprints(),
                raw.startedSprints(),
                raw.createdSprints(),
                raw.completedSprints(),
                projects
        );
    }

    private List<ProjectProgressItem> mapProjects(
            List<ProjectRawItem> rawProjects,
            Map<UUID, EmployeeResult> employeeById
    ) {
        if (rawProjects == null || rawProjects.isEmpty()) return List.of();

        List<ProjectProgressItem> projects = new ArrayList<>();

        for (ProjectRawItem p : rawProjects) {
            long backlog = p.backlogCount();
            long pending = p.pendingCount();
            long inProgress = p.inProgressCount();
            long completed = p.completedCount();

            long totalStories = backlog + pending + inProgress + completed;

            double progressPercentage = calculateProgress(totalStories, completed, inProgress);

            List<SprintCurrent> currentSprints = mapCurrentSprints(p.startedSprintsRaw());

            List<EmployeeResult> members = mapMembers(p.memberEmployeeIds(), employeeById);

            projects.add(new ProjectProgressItem(
                    p.projectId(),
                    p.projectKey(),
                    p.projectName(),

                    backlog,
                    pending,
                    inProgress,
                    completed,
                    totalStories,

                    progressPercentage,

                    p.totalSprints(),
                    p.startedSprints(),
                    p.createdSprints(),
                    p.completedSprints(),

                    currentSprints,
                    members
            ));
        }

        return projects;
    }

    private double calculateProgress(
            long totalStories,
            long completed,
            long inProgress
    ) {
        if (totalStories == 0L) return 0.0d;

        double doneEquivalent = (double) completed + (0.5d * (double) inProgress);
        return (doneEquivalent * 100.0d) / (double) totalStories;
    }

    private List<SprintCurrent> mapCurrentSprints(
            List<StartedSprintRaw> raws
    ) {
        if (raws == null || raws.isEmpty()) return List.of();

        List<SprintCurrent> sprints = new ArrayList<>();
        for (StartedSprintRaw s : raws) {
            sprints.add(new SprintCurrent(
                    s.sprintId(),
                    s.name(),
                    s.goal(),
                    s.status(),
                    s.startDate(),
                    s.endDate()
            ));
        }
        return sprints;
    }

    private List<EmployeeResult> mapMembers(
            Set<UUID> memberEmployeeIds,
            Map<UUID, EmployeeResult> employeeById
    ) {
        if (memberEmployeeIds == null || memberEmployeeIds.isEmpty()) return List.of();
        if (employeeById == null || employeeById.isEmpty()) return List.of();

        List<EmployeeResult> members = new ArrayList<>();
        for (UUID id : memberEmployeeIds) {
            EmployeeResult e = employeeById.get(id);
            if (e != null) {
                members.add(e);
            }
        }

        return members;
    }
}
