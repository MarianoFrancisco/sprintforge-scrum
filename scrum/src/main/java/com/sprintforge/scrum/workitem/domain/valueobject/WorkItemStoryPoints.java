package com.sprintforge.scrum.workitem.domain.valueobject;

import com.sprintforge.common.domain.exception.ValidationException;

public record WorkItemStoryPoints(Integer value) {
    public WorkItemStoryPoints {
        if (value != null && value < 0) {
            throw new ValidationException("Los puntos de la Historia de Usuario no pueden ser negativos");
        }
    }

    public static WorkItemStoryPoints ofNullable(Integer value) {
        return new WorkItemStoryPoints(value);
    }
}
