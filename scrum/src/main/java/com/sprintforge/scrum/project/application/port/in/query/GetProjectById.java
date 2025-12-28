package com.sprintforge.scrum.project.application.port.in.query;

import com.sprintforge.scrum.project.domain.Project;

public interface GetProjectById {
    Project handle(GetProjectByIdQuery query);
}
