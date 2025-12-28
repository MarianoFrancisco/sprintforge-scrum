package com.sprintforge.scrum.common.infrastructure.config.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final UrlProperties urlProperties;

    @Bean
    public RestClient employeeRestClient() {
        return RestClient.builder()
                .baseUrl(urlProperties.employee())
                .build();
    }
}
