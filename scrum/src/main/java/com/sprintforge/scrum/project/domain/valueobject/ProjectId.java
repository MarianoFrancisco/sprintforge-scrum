package com.sprintforge.scrum.project.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

import java.util.UUID;

public record ProjectId(UUID value) {
    public ProjectId {
        if (value == null) {
            throw new ValidationException("El identificador del proyecto no puede estar vacío");
        }
    }

    public static ProjectId of(UUID value) {
        return new ProjectId(value);
    }
}
