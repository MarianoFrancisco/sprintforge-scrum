package com.sprintforge.scrum.workitem.application.port.in.query;

import java.util.UUID;

public record GetWorkItemResultByIdQuery(
        UUID id
) {
}
