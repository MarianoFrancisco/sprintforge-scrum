package com.sprintforge.scrum.project.application.port.in.query;

import java.util.UUID;

public record GetProjectResultByIdQuery(
        UUID id
) {
}
