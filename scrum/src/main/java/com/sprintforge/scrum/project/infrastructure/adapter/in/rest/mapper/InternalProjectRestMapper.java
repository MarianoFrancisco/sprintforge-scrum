package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.scrum.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectProgressReportQuery;
import com.sprintforge.scrum.project.application.port.result.ProjectProgressItem;
import com.sprintforge.scrum.project.application.port.result.ProjectProgressReportResult;
import com.sprintforge.scrum.project.application.port.result.SprintCurrent;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto.*;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class InternalProjectRestMapper {
    public GetProjectProgressReportQuery toQuery(
            ProjectProgressReportRequestDTO dto
    ) {
        return new GetProjectProgressReportQuery(
                dto.projectId()
        );
    }

    public ProjectProgressReportResponseDTO fromResult(ProjectProgressReportResult result) {
        return new ProjectProgressReportResponseDTO(
                result.totalProjects(),
                result.totalSprints(),
                result.startedSprints(),
                result.createdSprints(),
                result.completedSprints(),
                mapProjects(result.projects())
        );
    }

    private List<ProjectProgressItemDTO> mapProjects(List<ProjectProgressItem> projects) {
        return projects.stream()
                .map(InternalProjectRestMapper::mapProject)
                .toList();
    }

    private ProjectProgressItemDTO mapProject(ProjectProgressItem p) {
        return new ProjectProgressItemDTO(
                p.projectId(),
                p.projectKey(),
                p.projectName(),

                p.backlogCount(),
                p.pendingCount(),
                p.inProgressCount(),
                p.completedCount(),
                p.totalStories(),

                p.progressPercentage(),

                p.totalSprints(),
                p.startedSprints(),
                p.createdSprints(),
                p.completedSprints(),

                mapSprints(p.currentSprints()),
                mapMembers(p.members())
        );
    }

    private List<SprintCurrentDTO> mapSprints(List<SprintCurrent> sprints) {
        return sprints.stream()
                .map(InternalProjectRestMapper::mapSprint)
                .toList();
    }

    private SprintCurrentDTO mapSprint(SprintCurrent s) {
        return new SprintCurrentDTO(
                s.sprintId(),
                s.name(),
                s.goal(),
                s.status(),
                s.startDate(),
                s.endDate()
        );
    }

    private List<EmployeeDTO> mapMembers(List<EmployeeResult> members) {
        return members.stream()
                .map(InternalProjectRestMapper::mapMember)
                .toList();
    }

    private EmployeeDTO mapMember(EmployeeResult e) {
        return new EmployeeDTO(
                e.id(),
                e.email(),
                e.fullName(),
                e.profileImage(),
                e.position()
        );
    }
}
