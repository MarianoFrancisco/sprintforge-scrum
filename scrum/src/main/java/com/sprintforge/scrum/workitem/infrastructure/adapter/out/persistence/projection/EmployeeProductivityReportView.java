package com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.projection;

import java.util.UUID;

public interface EmployeeProductivityReportView {

    UUID getEmployeeId();

    Long getWorkedStories();

    Long getCompletedStories();

    Long getPendingStories();

    Long getDeliveredStoryPoints();
}
