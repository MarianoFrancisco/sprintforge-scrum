package com.sprintforge.scrum.project.application.port.in.query;

import com.sprintforge.scrum.project.application.port.result.ProjectResult;

public interface GetProjectResultById {
    ProjectResult handle(GetProjectResultByIdQuery query);
}
