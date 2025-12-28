package com.sprintforge.scrum.project.application.port.in.command;

import com.sprintforge.scrum.project.domain.Project;

public interface UpdateProjectAmounts {
    Project handle(UpdateProjectAmountsCommand command);
}
