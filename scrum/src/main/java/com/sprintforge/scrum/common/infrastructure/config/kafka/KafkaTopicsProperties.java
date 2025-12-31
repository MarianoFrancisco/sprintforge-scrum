package com.sprintforge.scrum.common.infrastructure.config.kafka;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaTopicsProperties {
    private String projectCreated;
    private String paymentMade;
    private String sprintCreated;
}
