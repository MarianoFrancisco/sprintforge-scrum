package com.sprintforge.scrum.workitem.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

public class InvalidWorkItemPositionException extends BusinessException {

    private InvalidWorkItemPositionException(String message) {
        super(message);
    }

    public static InvalidWorkItemPositionException negative() {
        return new InvalidWorkItemPositionException(
                "La posición de una historia de usuario no puede ser negativa."
        );
    }

    public static InvalidWorkItemPositionException outOfRange(int position, int maxPosition) {
        return new InvalidWorkItemPositionException(
                "La posición de una historia de usuario (" + position +
                        ") excede el máximo permitido (" + maxPosition + ")."
        );
    }
}
