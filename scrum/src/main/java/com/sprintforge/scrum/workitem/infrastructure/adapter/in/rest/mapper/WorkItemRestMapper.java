package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.mapper;

import com.sprintforge.scrum.board.infrastructure.adapter.in.rest.mapper.BoardColumnRestMapper;
import com.sprintforge.scrum.common.infrastructure.adapter.in.rest.mapper.EmployeeResultRestMapper;
import com.sprintforge.scrum.project.infrastructure.adapter.in.rest.mapper.ProjectRestMapper;
import com.sprintforge.scrum.sprint.infrastructure.adapter.in.rest.mapper.SprintRestMapper;
import com.sprintforge.scrum.workitem.application.port.in.command.*;
import com.sprintforge.scrum.workitem.application.port.in.query.GetAllWorkItemsQuery;
import com.sprintforge.scrum.workitem.application.port.in.query.GetWorkItemResultByIdQuery;
import com.sprintforge.scrum.workitem.application.result.WorkItemResult;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto.*;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class WorkItemRestMapper {

    public GetAllWorkItemsQuery toQuery(
            UUID projectId,
            UUID sprintId,
            UUID boardColumnId,
            Integer priority,
            UUID developerId,
            UUID productOwnerId,
            String searchTerm
    ) {
        return new GetAllWorkItemsQuery(
                projectId,
                sprintId,
                boardColumnId,
                priority,
                developerId,
                productOwnerId,
                searchTerm
        );
    }

    public GetWorkItemResultByIdQuery toQuery(UUID id) {
        return new GetWorkItemResultByIdQuery(id);
    }

    public CreateWorkItemCommand toCreateCommand(CreateWorkItemRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return new CreateWorkItemCommand(
                dto.employeeId(),
                dto.projectId(),
                dto.sprintId(),
                dto.boardColumnId(),
                dto.title(),
                dto.description(),
                dto.acceptanceCriteria(),
                dto.storyPoints(),
                dto.priority(),
                dto.developerId(),
                dto.productOwnerId()
        );
    }

    public WorkItemResponseDTO toResponse(WorkItem workItem) {
        return new WorkItemResponseDTO(
                workItem.getId().value(),
                workItem.getPosition().value(),
                workItem.getTitle().value(),
                workItem.getDescription().value(),
                workItem.getAcceptanceCriteria().value(),
                workItem.getStoryPoints() != null ? workItem.getStoryPoints().value() : null,
                workItem.getPriority().value(),
                workItem.getDeveloperId() != null ? workItem.getDeveloperId().value() : null,
                workItem.getProductOwnerId() != null ? workItem.getProductOwnerId().value() : null,
                workItem.isDeleted(),
                workItem.getCreatedAt(),
                workItem.getUpdatedAt(),
                ProjectRestMapper.toResponse(workItem.getProject()),
                SprintRestMapper.toResponse(workItem.getSprint()),
                BoardColumnRestMapper.toResponse(workItem.getBoardColumn())
        );
    }

    public WorkItemResultResponseDTO toResultResponse(
            WorkItemResult workItem
    ) {
        if (workItem == null) {
            return null;
        }
        return new WorkItemResultResponseDTO(
                workItem.id(),
                workItem.position(),
                workItem.title(),
                workItem.description(),
                workItem.acceptanceCriteria(),
                workItem.storyPoints(),
                workItem.priority(),
                EmployeeResultRestMapper.toResponse(workItem.developer()),
                EmployeeResultRestMapper.toResponse(workItem.productOwner()),
                workItem.isDeleted(),
                workItem.createdAt(),
                workItem.updatedAt(),
                ProjectRestMapper.toResponse(workItem.project()),
                SprintRestMapper.toResponse(workItem.sprint()),
                BoardColumnRestMapper.toResponse(workItem.boardColumn())
        );
    }

    public AssignWorkItemDeveloperCommand toAssignDeveloperCommand(
            UUID id,
            AssignWorkItemDeveloperRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new AssignWorkItemDeveloperCommand(
                dto.employeeId(),
                id,
                dto.developerId()
        );
    }

    public UnassignWorkItemDeveloperCommand toUnassignDeveloperCommand(
            UUID id,
            UnassignWorkItemDeveloperRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UnassignWorkItemDeveloperCommand(
                dto.employeeId(),
                id
        );
    }

    public AssignWorkItemProductOwnerCommand toAssignProductOwnerCommand(
            UUID id,
            AssignWorkItemProductOwnerRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new AssignWorkItemProductOwnerCommand(
                dto.employeeId(),
                id,
                dto.productOwnerId()
        );
    }

    public UnassignWorkItemProductOwnerCommand toUnassignProductOwnerCommand(
            UUID id,
            UnassignWorkItemProductOwnerRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UnassignWorkItemProductOwnerCommand(
                dto.employeeId(),
                id
        );
    }

    public MoveWorkItemsBetweenBoardColumnsCommand toMoveBetweenColumnsCommand(
            MoveWorkItemsBetweenBoardColumnsRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new MoveWorkItemsBetweenBoardColumnsCommand(
                dto.employeeId(),
                dto.sprintId(),
                dto.targetBoardColumnId(),
                dto.targetPosition(),
                dto.ids()
        );
    }

    public MoveWorkItemsToBacklogCommand toMoveToBacklogCommand(
            MoveWorkItemsToBacklogRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new MoveWorkItemsToBacklogCommand(
                dto.employeeId(),
                dto.ids()
        );
    }

    public MoveWorkItemsToSprintCommand toMoveToSprintCommand(
            MoveWorkItemsToSprintRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new MoveWorkItemsToSprintCommand(
                dto.employeeId(),
                dto.ids(),
                dto.sprintId()
        );
    }

    public UpdateWorkItemTitleCommand toUpdateTitleCommand(
            UUID id,
            UpdateWorkItemTitleRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UpdateWorkItemTitleCommand(
                dto.employeeId(),
                id,
                dto.title()
        );
    }

    public UpdateWorkItemDescriptionCommand toUpdateDescriptionCommand(
            UUID id,
            UpdateWorkItemDescriptionRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UpdateWorkItemDescriptionCommand(
                dto.employeeId(),
                id,
                dto.description()
        );
    }

    public UpdateWorkItemAcceptanceCriteriaCommand toUpdateAcceptanceCriteriaCommand(
            UUID id,
            UpdateWorkItemAcceptanceCriteriaRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UpdateWorkItemAcceptanceCriteriaCommand(
                dto.employeeId(),
                id,
                dto.acceptanceCriteria()
        );
    }

    public UpdateWorkItemPriorityCommand toUpdatePriorityCommand(
            UUID id,
            UpdateWorkItemPriorityRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UpdateWorkItemPriorityCommand(
                dto.employeeId(),
                id,
                dto.priority()
        );
    }

    public UpdateWorkItemStoryPointsCommand toUpdateStoryPointsCommand(
            UUID id,
            UpdateWorkItemStoryPointsRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new UpdateWorkItemStoryPointsCommand(
                dto.employeeId(),
                id,
                dto.storyPoints()
        );
    }

    public MoveWorkItemInBoardColumnCommand toMoveInBoardColumnCommand(
            UUID id,
            MoveWorkItemInBoardColumnRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new MoveWorkItemInBoardColumnCommand(
                dto.employeeId(),
                id,
                dto.newPosition()
        );
    }

    public DeleteWorkItemCommand toDeleteCommand(
            UUID id,
            DeleteWorkItemRequestDTO dto
    ) {
        if (dto == null) {
            return null;
        }
        return new DeleteWorkItemCommand(
                dto.employeeId(),
                id
        );
    }

}
