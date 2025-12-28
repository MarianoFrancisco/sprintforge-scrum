package com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.mapper.ProjectRestMapper;
import com.sprintforge.scrum.sprint.application.port.in.command.*;
import com.sprintforge.scrum.sprint.application.port.in.query.GetAllSprintsQuery;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintByIdQuery;
import com.sprintforge.scrum.sprint.domain.Sprint;
import com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.dto.*;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class SprintRestMapper {
    public GetAllSprintsQuery toQuery(String searchTerm, String status) {
        return new GetAllSprintsQuery(searchTerm, status);
    }

    public GetSprintByIdQuery toQueryById(UUID id) {
        return new GetSprintByIdQuery(id);
    }

    public CreateSprintCommand toCreateCommand(CreateSprintRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return new CreateSprintCommand(
                dto.employeeId(),
                dto.projectId(),
                dto.name(),
                dto.goal(),
                dto.startDate(),
                dto.endDate()
        );
    }

    public SprintResponseDTO toResponse(Sprint sprint) {
        if (sprint == null) {
            return null;
        }
        return new SprintResponseDTO(
                sprint.getId().value(),
                sprint.getName().value(),
                sprint.getGoal() != null ? sprint.getGoal().value() : null,
                sprint.getStatus(),
                sprint.getStartDate().value(),
                sprint.getEndDate().value(),
                sprint.isDeleted(),
                sprint.getCreatedAt(),
                sprint.getUpdatedAt(),
                ProjectRestMapper.toResponse(sprint.getProject())
        );
    }

    public UpdateSprintNameCommand toUpdateNameCommand(
            UUID id,
            UpdateSprintNameRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UpdateSprintNameCommand(
                dto.employeeId(),
                id,
                dto.name()
        );
    }

    public UpdateSprintGoalCommand toUpdateGoalCommand(
            UUID id,
            UpdateSprintGoalRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UpdateSprintGoalCommand(
                dto.employeeId(),
                id,
                dto.goal()
        );
    }

    public UpdateSprintDatesCommand toUpdateDatesCommand(
            UUID id,
            UpdateSprintDatesRequestDTO dto
    ) {
        return new UpdateSprintDatesCommand(
                dto.employeeId(),
                id,
                dto.startDate(),
                dto.endDate()
        );
    }

    public DeleteSprintCommand toDeleteCommand(
            UUID id,
            DeleteSprintRequestDTO dto
    ) {
        return new DeleteSprintCommand(
                dto.employeeId(),
                id
        );
    }
}
