package com.sprintforge.scrum.project.application.port.out.persistence;

import java.util.UUID;

public interface ExistsProjectByNameAndIdNot {
    boolean existsByNameAndIdNot(String name, UUID id);
}
