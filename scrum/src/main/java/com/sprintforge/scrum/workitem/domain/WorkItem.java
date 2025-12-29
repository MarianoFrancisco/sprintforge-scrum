package com.sprintforge.scrum.workitem.domain;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.sprint.domain.Sprint;
import com.sprintforge.scrum.workitem.domain.valueobject.*;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Getter
public class WorkItem {

    private final WorkItemId id;

    private WorkItemPosition position;

    private WorkItemTitle title;
    private WorkItemDescription description;
    private WorkItemAcceptanceCriteria acceptanceCriteria;

    private WorkItemStoryPoints storyPoints;
    private WorkItemPriority priority;

    private WorkItemDeveloperId developerId;
    private WorkItemProductOwnerId productOwnerId;

    private boolean isDeleted;

    private final Instant createdAt;
    private Instant updatedAt;

    private Project project;
    private Sprint sprint;
    private BoardColumn boardColumn;

    public WorkItem(
            int position,
            String title,
            String description,
            String acceptanceCriteria,
            Integer storyPoints,
            int priority,
            UUID developerId,
            UUID productOwnerId,
            Project project,
            Sprint sprint,
            BoardColumn boardColumn
    ) {
        Instant now = now();

        this.id = WorkItemId.of(randomUUID());

        this.position = WorkItemPosition.of(position);

        this.title = WorkItemTitle.of(title);
        this.description = WorkItemDescription.of(description);
        this.acceptanceCriteria = WorkItemAcceptanceCriteria.of(acceptanceCriteria);

        this.storyPoints = WorkItemStoryPoints.ofNullable(storyPoints);
        this.priority = WorkItemPriority.of(priority);

        this.developerId = WorkItemDeveloperId.ofNullable(developerId);
        this.productOwnerId = WorkItemProductOwnerId.ofNullable(productOwnerId);


        this.isDeleted = false;
        this.createdAt = now;
        this.updatedAt = now;

        this.project = project;
        this.sprint = sprint;
        this.boardColumn = boardColumn;

        validateBacklogConstraint();
    }

    public WorkItem(
            UUID id,
            int position,
            String title,
            String description,
            String acceptanceCriteria,
            Integer storyPoints,
            int priority,
            UUID developerId,
            UUID productOwnerId,
            boolean isDeleted,
            Instant createdAt,
            Instant updatedAt,
            Project project,
            Sprint sprint,
            BoardColumn boardColumn
    ) {
        this.id = WorkItemId.of(id);

        this.position = WorkItemPosition.of(position);

        this.title = WorkItemTitle.of(title);
        this.description = WorkItemDescription.of(description);
        this.acceptanceCriteria = WorkItemAcceptanceCriteria.of(acceptanceCriteria);

        this.storyPoints = WorkItemStoryPoints.ofNullable(storyPoints);
        this.priority = WorkItemPriority.of(priority);

        this.developerId = WorkItemDeveloperId.ofNullable(developerId);
        this.productOwnerId = WorkItemProductOwnerId.ofNullable(productOwnerId);

        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.project = project;
        this.sprint = sprint;
        this.boardColumn = boardColumn;

        validateBacklogConstraint();
    }

    public void updateTitle(String newTitle) {
        performUpdate();
        this.title = WorkItemTitle.of(newTitle);
    }

    public void updateDescription(String newDescription) {
        performUpdate();
        this.description = WorkItemDescription.of(newDescription);
    }

    public void updateAcceptanceCriteria(String newAcceptanceCriteria) {
        performUpdate();
        this.acceptanceCriteria = WorkItemAcceptanceCriteria.of(newAcceptanceCriteria);
    }

    public void updateStoryPoints(Integer newStoryPoints) {
        performUpdate();
        this.storyPoints = WorkItemStoryPoints.ofNullable(newStoryPoints);
    }

    public void updatePriority(int newPriority) {
        performUpdate();
        this.priority = WorkItemPriority.of(newPriority);
    }

    public void assignDeveloper(UUID newDeveloperId) {
        performUpdate();
        this.developerId = WorkItemDeveloperId.ofNullable(newDeveloperId);
    }

    public void unassignDeveloper() {
        performUpdate();
        this.developerId = null;
    }

    public void assignProductOwner(UUID newProductOwnerId) {
        performUpdate();
        this.productOwnerId = WorkItemProductOwnerId.ofNullable(newProductOwnerId);
    }

    public void unassignProductOwner() {
        performUpdate();
        this.productOwnerId = null;
    }

    public void moveToBacklog(int newBacklogPosition) {
        performUpdate();
        if (this.sprint != null) {
            this.sprint = null;
        }
        if (this.boardColumn != null) {
            this.boardColumn = null;
        }
        this.position = WorkItemPosition.of(newBacklogPosition);
        validateBacklogConstraint();
    }

    public void moveToSprint(Sprint sprint, BoardColumn boardColumn, int newPosition) {
        performUpdate();
        if (sprint == null) {
            throw new ValidationException("No se puede mover a un sprint indefinido");
        }
        if (boardColumn == null) {
            throw new ValidationException("No se puede mover a una columna indefinida dentro del sprint");
        }
        this.sprint = sprint;
        this.boardColumn = boardColumn;
        this.position = WorkItemPosition.of(newPosition);
        validateBacklogConstraint();
    }

    public void moveToBoardColumn(BoardColumn boardColumn, int newPosition) {
        performUpdate();
        if (this.sprint == null && boardColumn != null) {
            throw new ValidationException("No se puede mover a una columna si la historia de usuario está en backlog");
        }
        this.boardColumn = boardColumn;
        this.position = WorkItemPosition.of(newPosition);
        validateBacklogConstraint();
    }

    public void updatePosition(int newPosition) {
        performUpdate();
        this.position = WorkItemPosition.of(newPosition);
    }

    public void delete() {
        if (this.isDeleted) {
            throw new ValidationException("La Historia de Usuario ya se encuentra eliminada");
        }
        this.isDeleted = true;
        this.updatedAt = now();
    }

    private void validateNotDeleted() {
        if (this.isDeleted) {
            throw new ValidationException("No se puede operar sobre una Historia de Usuario eliminada");
        }
    }

    private void performUpdate() {
        validateNotDeleted();
        this.updatedAt = now();
    }

    private void validateBacklogConstraint() {
        boolean backlog = (this.sprint == null && this.boardColumn == null);
        boolean inSprint = (this.sprint != null);

        if (!(backlog || inSprint)) {
            throw new ValidationException(
                    "Si la Historia de Usuario está en backlog no puede tener columna y si tiene columna debe pertenecer a un sprint"
            );
        }
    }
}
