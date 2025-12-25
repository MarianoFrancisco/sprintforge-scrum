package com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.controller;

import com.sprintforge.scrum.workitem.application.port.in.command.*;
import com.sprintforge.scrum.workitem.application.port.in.query.GetAllWorkItems;
import com.sprintforge.scrum.workitem.application.port.in.query.GetWorkItemById;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.dto.*;
import com.sprintforge.scrum.workitem.infrastructure.adapter.in.rest.mapper.WorkItemRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/work-item")
public class WorkItemController {

    private final GetAllWorkItems getAllWorkItems;
    private final GetWorkItemById getWorkItemById;

    private final CreateWorkItem createWorkItem;
    private final DeleteWorkItem deleteWorkItem;

    private final AssignWorkItemDeveloper assignWorkItemDeveloper;
    private final UnassignWorkItemDeveloper unassignWorkItemDeveloper;

    private final AssignWorkItemProductOwner assignWorkItemProductOwner;
    private final UnassignWorkItemProductOwner unassignWorkItemProductOwner;

    private final UpdateWorkItemTitle updateWorkItemTitle;
    private final UpdateWorkItemDescription updateWorkItemDescription;
    private final UpdateWorkItemAcceptanceCriteria updateWorkItemAcceptanceCriteria;
    private final UpdateWorkItemStoryPoints updateWorkItemStoryPoints;
    private final UpdateWorkItemPriority updateWorkItemPriority;

    private final MoveWorkItemInBoardColumn moveWorkItemInBoardColumn;
    private final MoveWorkItemsBetweenBoardColumns moveWorkItemsBetweenBoardColumns;
    private final MoveWorkItemsToBacklog moveWorkItemsToBacklog;
    private final MoveWorkItemsToSprint moveWorkItemsToSprint;

    @GetMapping
    public List<WorkItemResponseDTO> getAll(
            @RequestParam(required = false) UUID projectId,
            @RequestParam(required = false) UUID sprintId,
            @RequestParam(required = false) UUID boardColumnId,
            @RequestParam(required = false) Integer priority,
            @RequestParam(required = false) UUID developerId,
            @RequestParam(required = false) UUID productOwnerId,
            @RequestParam(required = false) String searchTerm
    ) {
        List<WorkItem> workItems = getAllWorkItems.handle(
                WorkItemRestMapper.toQuery(
                        projectId,
                        sprintId,
                        boardColumnId,
                        priority,
                        developerId,
                        productOwnerId,
                        searchTerm
                )
        );

        return workItems.stream()
                .map(WorkItemRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public WorkItemResponseDTO getById(@PathVariable UUID id) {
        WorkItem workItem = getWorkItemById.handle(
                WorkItemRestMapper.toQuery(id)
        );
        return WorkItemRestMapper.toResponse(workItem);
    }

    @PostMapping
    @ResponseStatus(code = CREATED)
    public WorkItemResponseDTO create(@RequestBody @Valid CreateWorkItemRequestDTO dto) {
        WorkItem created = createWorkItem.handle(
                WorkItemRestMapper.toCreateCommand(dto)
        );
        return WorkItemRestMapper.toResponse(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(
            @PathVariable UUID id,
            @RequestBody @Valid DeleteWorkItemRequestDTO dto
    ) {
        deleteWorkItem.handle(
                WorkItemRestMapper.toDeleteCommand(id, dto)
        );
    }

    @PatchMapping("/{id}/assign/developer")
    public WorkItemResponseDTO assignDeveloper(
            @PathVariable UUID id,
            @RequestBody @Valid AssignWorkItemDeveloperRequestDTO dto
    ) {
        WorkItem updated = assignWorkItemDeveloper.handle(
                WorkItemRestMapper.toAssignDeveloperCommand(id, dto)
        );
        return WorkItemRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/unassign/developer")
    public WorkItemResponseDTO unassignDeveloper(
            @PathVariable UUID id,
            @RequestBody @Valid UnassignWorkItemDeveloperRequestDTO dto
    ) {
        WorkItem updated = unassignWorkItemDeveloper.handle(
                WorkItemRestMapper.toUnassignDeveloperCommand(id, dto)
        );
        return WorkItemRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/assign/product-owner")
    public WorkItemResponseDTO assignProductOwner(
            @PathVariable UUID id,
            @RequestBody @Valid AssignWorkItemProductOwnerRequestDTO dto
    ) {
        WorkItem updated = assignWorkItemProductOwner.handle(
                WorkItemRestMapper.toAssignProductOwnerCommand(id, dto)
        );
        return WorkItemRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/unassign/product-owner")
    public WorkItemResponseDTO unassignProductOwner(
            @PathVariable UUID id,
            @RequestBody @Valid UnassignWorkItemProductOwnerRequestDTO dto
    ) {
        WorkItem updated = unassignWorkItemProductOwner.handle(
                WorkItemRestMapper.toUnassignProductOwnerCommand(id, dto)
        );
        return WorkItemRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/title")
    public WorkItemResponseDTO updateTitle(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateWorkItemTitleRequestDTO dto
    ) {
        WorkItem updated = updateWorkItemTitle.handle(
                WorkItemRestMapper.toUpdateTitleCommand(id, dto)
        );
        return WorkItemRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/description")
    public WorkItemResponseDTO updateDescription(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateWorkItemDescriptionRequestDTO dto
    ) {
        WorkItem updated = updateWorkItemDescription.handle(
                WorkItemRestMapper.toUpdateDescriptionCommand(id, dto)
        );
        return WorkItemRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/acceptance-criteria")
    public WorkItemResponseDTO updateAcceptanceCriteria(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateWorkItemAcceptanceCriteriaRequestDTO dto
    ) {
        WorkItem updated = updateWorkItemAcceptanceCriteria.handle(
                WorkItemRestMapper.toUpdateAcceptanceCriteriaCommand(id, dto)
        );
        return WorkItemRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/story-points")
    public WorkItemResponseDTO updateStoryPoints(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateWorkItemStoryPointsRequestDTO dto
    ) {
        WorkItem updated = updateWorkItemStoryPoints.handle(
                WorkItemRestMapper.toUpdateStoryPointsCommand(id, dto)
        );
        return WorkItemRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/priority")
    public WorkItemResponseDTO updatePriority(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateWorkItemPriorityRequestDTO dto
    ) {
        WorkItem updated = updateWorkItemPriority.handle(
                WorkItemRestMapper.toUpdatePriorityCommand(id, dto)
        );
        return WorkItemRestMapper.toResponse(updated);
    }

    @PatchMapping("/{id}/move")
    public WorkItemResponseDTO moveInBoardColumn(
            @PathVariable UUID id,
            @RequestBody @Valid MoveWorkItemInBoardColumnRequestDTO dto
    ) {
        WorkItem updated = moveWorkItemInBoardColumn.handle(
                WorkItemRestMapper.toMoveInBoardColumnCommand(id, dto)
        );
        return WorkItemRestMapper.toResponse(updated);
    }

    @PatchMapping("/move/between-columns")
    public void moveBetweenColumns(
            @RequestBody @Valid MoveWorkItemsBetweenBoardColumnsRequestDTO dto
    ) {
        moveWorkItemsBetweenBoardColumns.handle(
                WorkItemRestMapper.toMoveBetweenColumnsCommand(dto)
        );
    }

    @PatchMapping("/move/to-backlog")
    public void moveToBacklog(
            @RequestBody @Valid MoveWorkItemsToBacklogRequestDTO dto
    ) {
        moveWorkItemsToBacklog.handle(
                WorkItemRestMapper.toMoveToBacklogCommand(dto)
        );
    }

    @PatchMapping("/move/to-sprint")
    public void moveToSprint(
            @RequestBody @Valid MoveWorkItemsToSprintRequestDTO dto
    ) {
        moveWorkItemsToSprint.handle(
                WorkItemRestMapper.toMoveToSprintCommand(dto)
        );
    }
}
