package com.sprintforge.scrum.common.infrastructure.config.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "urls")
public record UrlProperties(
        String employee
) {
}
