package com.sprintforge.scrum.workitem.application.result;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.project.domain.Project;
import com.sprintforge.scrum.sprint.domain.Sprint;

import java.time.Instant;
import java.util.UUID;

public record WorkItemResult(
        UUID id,
        int position,
        String title,
        String description,
        String acceptanceCriteria,
        Integer storyPoints,
        int priority,
        EmployeeResult developer,
        EmployeeResult productOwner,
        boolean isDeleted,
        Instant createdAt,
        Instant updatedAt,
        Project project,
        Sprint sprint,
        BoardColumn boardColumn
) {
}
