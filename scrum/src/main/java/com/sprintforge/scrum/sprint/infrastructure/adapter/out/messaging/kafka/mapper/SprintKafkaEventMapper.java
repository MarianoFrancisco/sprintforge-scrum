package com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.mapper;

import com.sprintforge.scrum.sprint.application.port.out.event.SprintCreatedIntegrationEvent;
import com.sprintforge.scrum.sprint.application.port.out.event.SprintStartedIntegrationEvent;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.event.SprintCreatedKafkaMessage;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.event.SprintStartedKafkaMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SprintKafkaEventMapper {

    public SprintCreatedKafkaMessage toMessage(SprintCreatedIntegrationEvent event) {
        return new SprintCreatedKafkaMessage(
                event.entityType(),
                event.eventType(),
                event.message(),
                event.occurredAt(),
                event.projectId(),
                event.sprintId()
        );
    }

    public SprintStartedKafkaMessage toMessage(SprintStartedIntegrationEvent event) {
        return new SprintStartedKafkaMessage(
                event.entityType(),
                event.eventType(),
                event.message(),
                event.occurredAt(),
                event.projectId(),
                event.sprintId()
        );
    }
}
