package com.sprintforge.scrum.sprint.application.port.out.persistence;

import com.sprintforge.scrum.sprint.domain.Sprint;

import java.util.Optional;
import java.util.UUID;

public interface FindSprintById {
    Optional<Sprint> findById(UUID id);
}
