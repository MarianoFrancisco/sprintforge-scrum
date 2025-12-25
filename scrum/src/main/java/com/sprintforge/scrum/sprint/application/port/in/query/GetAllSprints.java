package com.sprintforge.scrum.sprint.application.port.in.query;

import com.sprintforge.scrum.sprint.domain.Sprint;

import java.util.List;

public interface GetAllSprints {
    List<Sprint> handle(GetAllSprintsQuery query);
}