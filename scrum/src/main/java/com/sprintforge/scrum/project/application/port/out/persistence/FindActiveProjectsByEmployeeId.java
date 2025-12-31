package com.sprintforge.scrum.project.application.port.out.persistence;

import java.util.List;
import java.util.UUID;

import com.sprintforge.scrum.project.domain.Project;

public interface FindActiveProjectsByEmployeeId {
    List<Project> findActiveByEmployeeId(UUID employeeId);
}
