package com.sprintforge.scrum.workitem.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

import java.util.List;
import java.util.UUID;

public class WorkItemProjectMismatchException extends BusinessException {

    private WorkItemProjectMismatchException(String message) {
        super(message);
    }

    public static WorkItemProjectMismatchException mixedProjects(List<UUID> workItemIds) {
        return new WorkItemProjectMismatchException(
                "Las historias de usuario no pertenecen todas al mismo proyecto. " +
                        "Ids recibidos: " + workItemIds
        );
    }
}
