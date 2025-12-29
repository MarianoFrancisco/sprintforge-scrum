package com.sprintforge.scrum.project.infrastructure.adapter.in.rest.dto;

import java.util.UUID;

public record ProjectProgressReportRequestDTO(
        UUID projectId
) {
}
