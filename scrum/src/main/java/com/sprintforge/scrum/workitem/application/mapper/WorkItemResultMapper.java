package com.sprintforge.scrum.workitem.application.mapper;

import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.workitem.application.result.WorkItemResult;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class WorkItemResultMapper {
    public WorkItemResult toResult(WorkItem workItem, List<EmployeeResult> employees) {
        if (workItem == null) return null;

        return new WorkItemResult(
                workItem.getId().value(),
                workItem.getPosition().value(),
                workItem.getTitle().value(),
                workItem.getDescription().value(),
                workItem.getAcceptanceCriteria().value(),
                workItem.getStoryPoints() != null ? workItem.getStoryPoints().value() : null,
                workItem.getPriority().value(),
                employees.stream()
                        .filter(e -> workItem.getDeveloperId() != null && e.id().equals(workItem.getDeveloperId().value()))
                        .findFirst()
                        .orElse(null),
                employees.stream()
                        .filter(e -> workItem.getProductOwnerId() != null && e.id().equals(workItem.getProductOwnerId().value()))
                        .findFirst()
                        .orElse(null),
                workItem.isDeleted(),
                workItem.getCreatedAt(),
                workItem.getUpdatedAt(),
                workItem.getProject(),
                workItem.getSprint(),
                workItem.getBoardColumn()
        );
    }
}
