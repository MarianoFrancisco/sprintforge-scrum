package com.sprintforge.scrum.project.application.port.out.persistence;

import com.sprintforge.scrum.project.domain.Project;

import java.util.Optional;
import java.util.UUID;

public interface FindProjectById {
    Optional<Project> findById(UUID id);
}
