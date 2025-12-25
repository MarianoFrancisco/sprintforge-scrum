package com.sprintforge.scrum.sprint.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

public class DuplicateSprintException extends BusinessException {
    public DuplicateSprintException(String message) {
        super(message);
    }
}
