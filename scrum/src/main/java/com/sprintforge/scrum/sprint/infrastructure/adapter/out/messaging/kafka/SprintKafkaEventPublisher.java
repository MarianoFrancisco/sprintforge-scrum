package com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka;

import com.sprintforge.scrum.common.infrastructure.config.kafka.KafkaTopicsProperties;
import com.sprintforge.scrum.sprint.application.port.out.event.*;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.event.SprintCompletedKafkaMessage;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.event.SprintCreatedKafkaMessage;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.event.SprintDeletedKafkaMessage;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.event.SprintStartedKafkaMessage;
import com.sprintforge.scrum.sprint.infrastructure.adapter.out.messaging.kafka.mapper.SprintKafkaEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@NullMarked
@Component
@RequiredArgsConstructor
public class SprintKafkaEventPublisher implements
        SprintEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topics;

    @Override
    public void publishSprintCreated(SprintCreatedIntegrationEvent event) {
        SprintCreatedKafkaMessage message = SprintKafkaEventMapper.toMessage(event);
        String topic = topics.getSprintCreated();
        String key = message.sprintId().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published SprintCreated event. topic={}, key={}", topic, key);
    }

    @Override
    public void publishSprintStarted(SprintStartedIntegrationEvent event) {
        SprintStartedKafkaMessage message = SprintKafkaEventMapper.toMessage(event);
        String topic = topics.getSprintStarted();
        String key = message.sprintId().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published SprintStarted event. topic={}, key={}", topic, key);
    }

    @Override
    public void publishSprintCompleted(SprintCompletedIntegrationEvent event) {
        SprintCompletedKafkaMessage message = SprintKafkaEventMapper.toMessage(event);
        String topic = topics.getSprintStarted();
        String key = message.sprintId().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published SprintCompleted event. topic={}, key={}", topic, key);
    }

    @Override
    public void publishSprintDeleted(SprintDeletedIntegrationEvent event) {
        SprintDeletedKafkaMessage message = SprintKafkaEventMapper.toMessage(event);
        String topic = topics.getSprintStarted();
        String key = message.sprintId().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published SprintDeleted event. topic={}, key={}", topic, key);
    }
}
