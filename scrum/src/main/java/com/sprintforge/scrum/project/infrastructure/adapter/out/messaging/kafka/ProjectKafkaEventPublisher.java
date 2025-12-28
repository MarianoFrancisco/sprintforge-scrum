package com.sprintforge.scrum.project.infrastructure.adapter.out.messaging.kafka;

import com.sprintforge.scrum.common.infrastructure.config.kafka.KafkaTopicsProperties;
import com.sprintforge.scrum.project.application.port.out.event.ProjectCreatedIntegrationEvent;
import com.sprintforge.scrum.project.application.port.out.event.ProjectEventPublisher;
import com.sprintforge.scrum.project.infrastructure.adapter.out.messaging.kafka.event.ProjectCreatedKafkaMessage;
import com.sprintforge.scrum.project.infrastructure.adapter.out.messaging.kafka.mapper.ProjectKafkaEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@NullMarked
@Component
@RequiredArgsConstructor
public class ProjectKafkaEventPublisher implements ProjectEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicsProperties topics;

    @Override
    public void publishProjectCreated(
            ProjectCreatedIntegrationEvent event
    ) {
        ProjectCreatedKafkaMessage message = ProjectKafkaEventMapper.toMessage(event);
        String topic = topics.getProjectCreated();
        String key = message.projectId().toString();

        kafkaTemplate.send(topic, key, message);

        log.debug("Published ProjectCreated event. topic={}, key={}", topic, key);
    }
}
