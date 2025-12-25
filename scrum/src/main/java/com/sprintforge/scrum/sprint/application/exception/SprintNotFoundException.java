package com.sprintforge.scrum.sprint.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

import java.util.UUID;

public class SprintNotFoundException extends BusinessException {

    private SprintNotFoundException(String field, String value) {
        super(String.format("No se encontró ningún sprint con %s \"%s\".", field, value));
    }

    public static SprintNotFoundException byId(UUID id) {
        return new SprintNotFoundException("el identificador", id.toString());
    }

    public static SprintNotFoundException byName(String name) {
        return new SprintNotFoundException("el nombre", name);
    }
}
