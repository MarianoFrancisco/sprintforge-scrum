package com.sprintforge.scrum.workitem.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record WorkItemPosition(int value) {
    public WorkItemPosition {
        if (value < 0) {
            throw new ValidationException("La posición de la Historia de Usuario no puede ser negativa");
        }
    }

    public static WorkItemPosition of(int value) {
        return new WorkItemPosition(value);
    }
}
