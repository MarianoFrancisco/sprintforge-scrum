package com.sprintforge.scrum.project.application.exception;

import com.sprintforge.common.application.exception.DuplicateEntityException;

public class DuplicateProjectException extends DuplicateEntityException {
    private DuplicateProjectException(String field, String value) {
        super(String.format("Ya existe un proyecto con %s \"%s\".", field, value));
    }

    public static DuplicateProjectException byName(String name) {
        return new DuplicateProjectException("el nombre", name);
    }

    public static DuplicateProjectException byProjectKey(String projectKey) {
        return new DuplicateProjectException("la clave", projectKey);
    }
}
