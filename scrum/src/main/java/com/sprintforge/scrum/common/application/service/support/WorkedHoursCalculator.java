package com.sprintforge.scrum.common.application.service.support;

import org.springframework.stereotype.Component;

@Component
public class WorkedHoursCalculator {

    private static final int HOURS_PER_STORY_POINT = 4;

    public long calculate(long deliveredStoryPoints) {
        return deliveredStoryPoints * HOURS_PER_STORY_POINT;
    }
}
