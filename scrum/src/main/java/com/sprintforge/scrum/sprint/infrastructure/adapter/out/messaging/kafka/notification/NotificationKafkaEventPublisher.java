package com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.notification;

import com.sprintforge.scrum.common.infrastructure.config.kafka.KafkaTopicsProperties;
import com.sprintforge.scrum.sprint.application.port.out.event.notification.EmailSprintCreatedIntegrationEvent;
import com.sprintforge.scrum.sprint.application.port.out.event.notification.NotificationEventPublisher;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.notification.event.EmailSprintCreatedKafkaMessage;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.notification.mapper.NotificationKafkaEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Slf4j
@NullMarked
@Component
@RequiredArgsConstructor
public class NotificationKafkaEventPublisher implements
        NotificationEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topics;

    @Override
    public void publishEmailSprintCreated(EmailSprintCreatedIntegrationEvent event) {
        EmailSprintCreatedKafkaMessage message = NotificationKafkaEventMapper.toMessage(event);
        String topic = topics.getEmailSprintCreated();
        String key = randomUUID().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published EmailSprintCreated event. topic={}, key={}", topic, key);
    }
}
