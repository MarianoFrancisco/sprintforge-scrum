package com.sprintforge.scrum.workitem.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

import java.util.UUID;

public class WorkItemNotInBoardColumnException extends BusinessException {

    private WorkItemNotInBoardColumnException(String message) {
        super(message);
    }

    public static WorkItemNotInBoardColumnException byId(UUID workItemId) {
        return new WorkItemNotInBoardColumnException(
                String.format(
                        "La historia de usuario \"%s\" no se encuentra en una columna del tablero.",
                        workItemId
                )
        );
    }
}
