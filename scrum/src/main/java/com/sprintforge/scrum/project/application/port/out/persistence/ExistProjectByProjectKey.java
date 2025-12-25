package com.sprintforge.scrum.project.application.port.out.persistence;

public interface ExistProjectByProjectKey {
    boolean existsByProjectKey(String projectKey);
}
