package com.sprintforge.scrum.sprint.application.port.in.command;

import com.sprintforge.scrum.sprint.domain.Sprint;

public interface CreateSprint {
    Sprint handle(CreateSprintCommand command);
}
