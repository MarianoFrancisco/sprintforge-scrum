package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.scrum.project.application.port.in.command.*;
import com.sprintforge.scrum.project.application.port.in.query.GetAllProjectsQuery;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectByIdQuery;
import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto.*;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.UUID;

@UtilityClass
public class ProjectRestMapper {
    public GetAllProjectsQuery toQuery(
            String searchTerm,
            Boolean isActive
    ) {
        return new GetAllProjectsQuery(
                searchTerm,
                isActive
        );
    }

    public GetProjectByIdQuery toQuery(UUID id) {
        return new GetProjectByIdQuery(id);
    }

    public CreateProjectCommand toCreateCommand(CreateProjectRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return new CreateProjectCommand(
                dto.employeeId(),
                dto.projectKey(),
                dto.name(),
                dto.description(),
                dto.client(),
                dto.area(),
                dto.budgetAmount(),
                dto.contractAmount(),
                dto.employeeIds()
        );
    }

    public ProjectResponseDTO toResponse(Project project) {
        if (project == null) {
            return null;
        }

        Set<ProjectEmployeeResponseDTO> assignments = project.getAssignments().stream()
                .map(ProjectEmployeeRestMapper::toResponse)
                .collect(java.util.stream.Collectors.toSet());

        return new ProjectResponseDTO(
                project.getId().value(),
                project.getProjectKey().value(),
                project.getName().value(),
                project.getDescription() != null ? project.getDescription().value() : null,
                project.getClient().value(),
                project.getArea().value(),
                project.getBudgetAmount() != null ? project.getBudgetAmount().value() : null,
                project.getContractAmount() != null ? project.getContractAmount().value() : null,
                project.isClosed(),
                project.isDeleted(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                assignments
        );
    }

    public UpdateProjectNameCommand toUpdateNameCommand(
            UUID id,
            UpdateProjectNameRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UpdateProjectNameCommand(
                dto.employeeId(),
                id,
                dto.name()
        );
    }

    public UpdateProjectDescriptionCommand toUpdateDescriptionCommand(
            UUID id,
            UpdateProjectDescriptionRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UpdateProjectDescriptionCommand(
                dto.employeeId(),
                id,
                dto.description()
        );
    }

    public UpdateProjectClientCommand toUpdateClientCommand(
            UUID id,
            UpdateProjectClientRequestDTO dto
    ) {
        return new UpdateProjectClientCommand(
                dto.employeeId(),
                id,
                dto.client()
        );
    }

    public UpdateProjectAreaCommand toUpdateAreaCommand(
            UUID id,
            UpdateProjectAreaRequestDTO dto
    ) {
        return new UpdateProjectAreaCommand(
                dto.employeeId(),
                id,
                dto.area()
        );
    }

    public UpdateProjectAmountsCommand toUpdateAmountsCommand(
            UUID id,
            UpdateProjectAmountsRequestDTO dto
    ) {
        return new UpdateProjectAmountsCommand(
                dto.employeeId(),
                id,
                dto.budgetAmount(),
                dto.contractAmount()
        );
    }

    public AssignProjectEmployeesCommand toAssignEmployeesCommand(
            UUID id,
            AssignProjectEmployeesRequestDTO dto
    ) {
        return new AssignProjectEmployeesCommand(
                dto.employeeId(),
                id,
                dto.employeeIds()
        );
    }

    public UnassignProjectEmployeesCommand toUnassignEmployeesCommand(
            UUID id,
            UnassignProjectEmployeesRequestDTO dto
    ) {
        return new UnassignProjectEmployeesCommand(
                dto.employeeId(),
                id,
                dto.employeeIds()
        );
    }

    public OpenProjectCommand toOpenCommand(
            UUID id,
            OpenProjectRequestDTO dto
    ) {
        return new OpenProjectCommand(
                id,
                dto.employeeId()
        );
    }

    public CloseProjectCommand toCloseCommand(
            UUID id,
            CloseProjectRequestDTO dto
    ) {
        return new CloseProjectCommand(
                id,
                dto.employeeId()
        );
    }

    public DeleteProjectCommand toDeleteCommand(
            UUID id,
            DeleteProjectRequestDTO dto
    ) {
        return new DeleteProjectCommand(
                id,
                dto.employeeId()
        );
    }
}
