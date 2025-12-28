package com.sprintforge.scrum.project.application.port.out.persistence;

import com.sprintforge.scrum.project.domain.Project;

public interface SaveProject {
    Project save(Project project);
}
