package com.sprintforge.scrum.sprint.application.mapper;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintCreatedIntegrationEvent;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintStartedIntegrationEvent;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.experimental.UtilityClass;

import static java.time.Instant.now;

@UtilityClass
public class SprintIntegrationMapper {

    public SprintCreatedIntegrationEvent sprintCreated(
            EmployeeResult employee,
            Sprint sprint
    ) {
        return buildCreatedEvent(
                employee,
                sprint,
                "SPRINT_CREATED",
                "ha creado"
        );
    }

    public SprintStartedIntegrationEvent sprintStarted(
            EmployeeResult employee,
            Sprint sprint
    ) {
        return buildStartedEvent(
                employee,
                sprint,
                "SPRINT_STARTED",
                "ha iniciado"
        );
    }

    private SprintCreatedIntegrationEvent buildCreatedEvent(
            EmployeeResult employee,
            Sprint sprint,
            String eventType,
            String action
    ) {
        if (employee == null || sprint == null) {
            return null;
        }

        return new SprintCreatedIntegrationEvent(
                "SPRINT",
                eventType,
                buildMessage(employee, action, sprint),
                now(),
                sprint.getProject().getId().value(),
                sprint.getId().value()
        );
    }

    private SprintStartedIntegrationEvent buildStartedEvent(
            EmployeeResult employee,
            Sprint sprint,
            String eventType,
            String action
    ) {
        if (employee == null || sprint == null) {
            return null;
        }

        return new SprintStartedIntegrationEvent(
                "SPRINT",
                eventType,
                buildMessage(employee, action, sprint),
                now(),
                sprint.getProject().getId().value(),
                sprint.getId().value()
        );
    }

    private String buildMessage(
            EmployeeResult employee,
            String action,
            Sprint sprint
    ) {
        return "El empleado " + employee.fullName()
                + " " + action
                + " el sprint " + sprint.getName().value();
    }
}
