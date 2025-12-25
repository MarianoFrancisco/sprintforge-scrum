package com.sprintforge.scrum.sprint.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

import java.util.UUID;

public record SprintId(UUID value) {
    public SprintId {
        if (value == null) {
            throw new ValidationException("El identificador del sprint no puede estar vacío");
        }
    }

    public static SprintId of(UUID value) {
        return new SprintId(value);
    }
}
