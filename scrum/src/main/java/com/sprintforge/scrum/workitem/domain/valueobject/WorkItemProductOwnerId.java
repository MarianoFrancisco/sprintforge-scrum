package com.sprintforge.scrum.workitem.domain.valueobject;

import java.util.UUID;

public record WorkItemProductOwnerId(UUID value) {
    public static WorkItemProductOwnerId ofNullable(UUID value) {
        return new WorkItemProductOwnerId(value);
    }
}
