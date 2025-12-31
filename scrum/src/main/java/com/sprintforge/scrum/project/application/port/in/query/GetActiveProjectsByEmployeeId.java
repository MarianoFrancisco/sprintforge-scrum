package com.sprintforge.scrum.project.application.port.in.query;

import java.util.List;
import java.util.UUID;

import com.sprintforge.scrum.project.domain.Project;

public interface GetActiveProjectsByEmployeeId {
    List<Project> handle(UUID employeeId);
}
