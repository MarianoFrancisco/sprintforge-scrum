package com.sprintforge.scrum.sprint.application.port.in.query;

import com.sprintforge.scrum.sprint.domain.Sprint;

public interface GetSprintById {
    Sprint handle(GetSprintByIdQuery query);
}