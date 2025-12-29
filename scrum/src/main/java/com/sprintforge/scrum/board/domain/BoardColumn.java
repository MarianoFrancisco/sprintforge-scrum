package com.sprintforge.scrum.board.domain;

import com.sprintforge.common.domain.exception.ValidationException;
import com.sprintforge.scrum.board.domain.valueobject.BoardColumnId;
import com.sprintforge.scrum.board.domain.valueobject.BoardColumnName;
import com.sprintforge.scrum.board.domain.valueobject.BoardColumnPosition;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Getter
public class BoardColumn {

    private final BoardColumnId id;

    private BoardColumnName name;
    private BoardColumnPosition position;

    private boolean isFinal;
    private boolean isDeleted;

    private final Instant createdAt;
    private Instant updatedAt;

    private final Sprint sprint;

    public BoardColumn(
            String name,
            int position,
            Sprint sprint,

    ) {
        Instant now = now();

        this.id = BoardColumnId.of(randomUUID());

        this.name = BoardColumnName.of(name);
        this.position = BoardColumnPosition.of(position);

        this.isFinal = true;
        this.isDeleted = false;

        this.createdAt = now;
        this.updatedAt = now;

        this.sprint = validateSprint(sprint);
    }

    public BoardColumn(
            UUID id,
            String name,
            int position,
            boolean isFinal,
            boolean isDeleted,
            Instant createdAt,
            Instant updatedAt,
            Sprint sprint
    ) {
        this.id = BoardColumnId.of(id);

        this.name = BoardColumnName.of(name);
        this.position = BoardColumnPosition.of(position);

        this.isFinal = isFinal;
        this.isDeleted = isDeleted;

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.sprint = sprint;
    }

    public void updateName(String newName) {
        performUpdate();
        this.name = BoardColumnName.of(newName);
    }

    public void updatePosition(int newPosition) {
        performUpdate();
        this.position = BoardColumnPosition.of(newPosition);
    }

    public void markAsFinal() {
        performUpdate();
        this.isFinal = true;
    }

    public void unmarkAsFinal() {
        performUpdate();
        this.isFinal = false;
    }

    public void delete() {
        if (this.isDeleted) {
            throw new ValidationException("La columna del tablero ya se encuentra eliminada");
        }
        this.isDeleted = true;
        this.updatedAt = now();
    }

    private void validateNotDeleted() {
        if (this.isDeleted) {
            throw new ValidationException("No se puede operar sobre una columna del tablero eliminada");
        }
    }

    private void performUpdate() {
        validateNotDeleted();
        this.updatedAt = now();
    }

    private Sprint validateSprint(Sprint sprint) {
        if (sprint == null) {
            throw new ValidationException("El sprint no puede estar vacío");
        }
        sprint.validateNotDeleted();
        return sprint;
    }
}
