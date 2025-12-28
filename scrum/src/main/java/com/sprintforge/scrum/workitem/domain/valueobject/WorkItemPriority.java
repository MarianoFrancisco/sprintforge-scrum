package com.sprintforge.scrum.workitem.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record WorkItemPriority(int value) {
    public WorkItemPriority {
        if (value < 1 || value > 5) {
            throw new ValidationException("La prioridad de la Historia de Usuario debe estar entre 1 y 5");
        }
    }

    public static WorkItemPriority of(int value) {
        return new WorkItemPriority(value);
    }
}
