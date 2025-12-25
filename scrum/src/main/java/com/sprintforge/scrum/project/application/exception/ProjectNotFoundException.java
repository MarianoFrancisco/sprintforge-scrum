package com.sprintforge.scrum.project.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

import java.util.UUID;

public class ProjectNotFoundException extends BusinessException {

    private ProjectNotFoundException(String field, String value) {
        super(String.format("No se encontró ningún proyecto con %s \"%s\".", field, value));
    }

    public static ProjectNotFoundException byId(UUID id) {
        return new ProjectNotFoundException("el identificador", id.toString());
    }

    public static ProjectNotFoundException byName(String name) {
        return new ProjectNotFoundException("el nombre", name);
    }

    public static ProjectNotFoundException byProjectKey(String projectKey) {
        return new ProjectNotFoundException("la clave", projectKey);
    }
}
