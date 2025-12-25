package com.sprintforge.scrum.workitem.application.service.internal;

import java.util.UUID;

public record OriginKey(
        UUID sprintId,
        UUID boardColumnId
) {
}
