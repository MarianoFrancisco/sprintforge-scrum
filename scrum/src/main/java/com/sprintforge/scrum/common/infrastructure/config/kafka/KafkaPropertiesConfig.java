package com.sprintforge.scrum.common.infrastructure.config.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaPropertiesConfig {

    @Bean(name = "kafkaTopicsProperties")
    @ConfigurationProperties(prefix = "spring.kafka.topics")
    public KafkaTopicsProperties kafkaTopicsProperties() {
        return new KafkaTopicsProperties();
    }

    @Bean(name = "kafkaGroupsProperties")
    @ConfigurationProperties(prefix = "spring.kafka.groups")
    public KafkaGroupsProperties kafkaGroupsProperties() {
        return new KafkaGroupsProperties();
    }
}
