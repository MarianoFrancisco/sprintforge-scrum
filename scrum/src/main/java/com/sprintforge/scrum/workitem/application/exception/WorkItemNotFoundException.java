package com.sprintforge.scrum.workitem.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

import java.util.List;
import java.util.UUID;

public class WorkItemNotFoundException extends BusinessException {

    private WorkItemNotFoundException(String message) {
        super(message);
    }

    private WorkItemNotFoundException(String field, String value) {
        super(String.format(
                "No se encontró ninguna historia de usuario con %s \"%s\".",
                field,
                value
        ));
    }

    public static WorkItemNotFoundException byId(UUID id) {
        return new WorkItemNotFoundException(
                "el identificador",
                id.toString()
        );
    }

    public static WorkItemNotFoundException missingIds(List<UUID> ids) {
        return new WorkItemNotFoundException(
                "los identificadores",
                ids.toString()
        );
    }
}
