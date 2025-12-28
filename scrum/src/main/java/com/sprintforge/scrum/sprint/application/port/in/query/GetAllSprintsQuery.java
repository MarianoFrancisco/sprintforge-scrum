package com.sprintforge.scrum.sprint.application.port.in.query;

public record GetAllSprintsQuery(
        String searchTerm,
        String status
) {
}