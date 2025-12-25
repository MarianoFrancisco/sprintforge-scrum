package com.sprintforge.scrum.workitem.domain.valueobject;

import java.util.UUID;

public record WorkItemDeveloperId(UUID value) {
    public static WorkItemDeveloperId ofNullable(UUID value) {
        return new WorkItemDeveloperId(value);
    }
}
