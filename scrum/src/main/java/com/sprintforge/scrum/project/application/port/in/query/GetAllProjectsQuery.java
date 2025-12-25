package com.sprintforge.scrum.project.application.port.in.query;

public record GetAllProjectsQuery(
        String searchTerm,
        Boolean isActive
) {
}
