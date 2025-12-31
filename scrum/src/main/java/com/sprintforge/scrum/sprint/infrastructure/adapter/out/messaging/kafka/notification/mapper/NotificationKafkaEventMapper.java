package com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.notification.mapper;

import com.sprintforge.scrum.sprint.application.port.out.event.notification.EmailSprintCreatedIntegrationEvent;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.notification.event.EmailSprintCreatedKafkaMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NotificationKafkaEventMapper {

    public EmailSprintCreatedKafkaMessage toMessage(EmailSprintCreatedIntegrationEvent event) {
        return new EmailSprintCreatedKafkaMessage(
                event.email(),
                event.fullName(),
                event.sprintName()
        );
    }
}
