package com.sprintforge.scrum.sprint.application.mapper;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintCompletedIntegrationEvent;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintCreatedIntegrationEvent;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintDeletedIntegrationEvent;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintStartedIntegrationEvent;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.experimental.UtilityClass;

import static java.time.Instant.now;

@UtilityClass
public class SprintIntegrationMapper {

    private final String ENTITY_TYPE = "SPRINT";

    public SprintCreatedIntegrationEvent sprintCreated(
            EmployeeResult employee,
            Sprint sprint
    ) {
        return new SprintCreatedIntegrationEvent(
                ENTITY_TYPE,
                "SPRINT_CREATED",
                buildMessage(employee, "ha creado", sprint),
                now(),
                sprint.getProject().getId().value(),
                sprint.getId().value()
        );
    }

    public SprintStartedIntegrationEvent sprintStarted(
            EmployeeResult employee,
            Sprint sprint
    ) {
        return new SprintStartedIntegrationEvent(
                ENTITY_TYPE,
                "SPRINT_STARTED",
                buildMessage(employee, "ha iniciado", sprint),
                now(),
                sprint.getProject().getId().value(),
                sprint.getId().value()
        );
    }

    public SprintCompletedIntegrationEvent sprintCompleted(
            EmployeeResult employee,
            Sprint sprint
    ) {
        return new SprintCompletedIntegrationEvent(
                ENTITY_TYPE,
                "SPRINT_COMPLETED",
                buildMessage(employee, "ha completado", sprint),
                now(),
                sprint.getProject().getId().value(),
                sprint.getId().value()
        );
    }

    public SprintDeletedIntegrationEvent sprintDeleted(
            EmployeeResult employee,
            Sprint sprint
    ) {
        return new SprintDeletedIntegrationEvent(
                ENTITY_TYPE,
                "SPRINT_DELETED",
                buildMessage(employee, "ha eliminado", sprint),
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
