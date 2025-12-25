package com.sprintforge.scrum.project.application.port.out.persistence;

import com.sprintforge.scrum.project.application.port.in.query.GetAllProjectsQuery;
import com.sprintforge.scrum.project.domain.Project;

import java.util.List;

public interface FindAllProjects {
    List<Project> findAll(GetAllProjectsQuery query);
}
