package com.sprintforge.scrum.sprint.application.mapper;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintCreatedIntegrationEvent;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.experimental.UtilityClass;

import static java.time.Instant.now;

@UtilityClass
public class SprintIntegrationMapper {
    public SprintCreatedIntegrationEvent from(
            EmployeeResult employee,
            Sprint sprint
    ) {
        if (sprint == null) {
            return null;
        }
        return new SprintCreatedIntegrationEvent(
                "SPRINT",
                "SPRINT_CREATED",
                "El empleado " + employee.fullName() + " ha creado el sprint " + sprint.getName().value(),
                now(),
                sprint.getProject().getId().value(),
                sprint.getId().value()
        );
    }
}
