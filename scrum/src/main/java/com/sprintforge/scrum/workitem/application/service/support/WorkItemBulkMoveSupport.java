package com.sprintforge.scrum.workitem.application.service.support;

import com.sprintforge.scrum.workitem.application.exception.WorkItemNotFoundException;
import com.sprintforge.scrum.workitem.application.exception.WorkItemProjectMismatchException;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindWorkItemsByIds;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

@Component
@RequiredArgsConstructor
public class WorkItemBulkMoveSupport {

    private final FindWorkItemsByIds findWorkItemsByIds;

    public List<WorkItem> loadAndValidate(List<UUID> ids) {
        List<WorkItem> items = findWorkItemsByIds.findAllByIds(ids);
        validateAllFound(ids, items);
        validateSameProject(ids, items);
        return items;
    }

    public UUID projectIdOf(List<WorkItem> items) {
        return items.getFirst().getProject().getId().value();
    }

    public Map<UUID, WorkItem> indexById(List<WorkItem> items) {
        return items.stream()
                .collect(toMap(w -> w.getId().value(), Function.identity()));
    }

    private void validateAllFound(List<UUID> requestedIds, List<WorkItem> items) {
        Set<UUID> foundIds = items.stream()
                .map(w -> w.getId().value())
                .collect(toSet());

        List<UUID> missing = requestedIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missing.isEmpty()) {
            throw WorkItemNotFoundException.missingIds(missing);
        }
    }

    private void validateSameProject(List<UUID> requestedIds, List<WorkItem> items) {
        UUID projectId = items.getFirst().getProject().getId().value();

        boolean mixedProjects = items.stream()
                .anyMatch(w -> !w.getProject().getId().value().equals(projectId));

        if (mixedProjects) {
            throw WorkItemProjectMismatchException.mixedProjects(requestedIds);
        }
    }
}
