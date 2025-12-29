package com.sprintforge.scrum.project.infrastructure.adapter.out.persistence.projection;

import java.util.UUID;

public interface ProjectMemberView {
    UUID getProjectId();

    UUID getEmployeeId();
}
