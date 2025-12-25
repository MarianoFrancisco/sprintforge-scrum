package com.sprintforge.scrum.sprint.application.port.out.persistence;

import com.sprintforge.scrum.sprint.application.port.in.query.GetAllSprintsQuery;
import com.sprintforge.scrum.sprint.domain.Sprint;

import java.util.List;

public interface FindAllSprints {
    List<Sprint> findAll(GetAllSprintsQuery query);
}
