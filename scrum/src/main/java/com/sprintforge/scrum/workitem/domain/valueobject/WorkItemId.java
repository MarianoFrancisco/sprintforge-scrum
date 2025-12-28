package com.sprintforge.scrum.workitem.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

import java.util.UUID;

public record WorkItemId(UUID value) {
    public WorkItemId {
        if (value == null) {
            throw new ValidationException("El identificador de la Historia de Usuario no puede estar vacío");
        }
    }

    public static WorkItemId of(UUID value) {
        return new WorkItemId(value);
    }
}
