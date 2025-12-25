package com.sprintforge.scrum.sprint.application.port.out.persistence;

import com.sprintforge.scrum.sprint.domain.Sprint;

public interface SaveSprint {
    Sprint save(Sprint sprint);
}
