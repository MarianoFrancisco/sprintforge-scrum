package com.sprintforge.scrum.project.application.port.in.query;

import com.sprintforge.scrum.project.domain.Project;

import java.util.List;

public interface GetAllProjects {
    List<Project> handle(GetAllProjectsQuery query);
}
