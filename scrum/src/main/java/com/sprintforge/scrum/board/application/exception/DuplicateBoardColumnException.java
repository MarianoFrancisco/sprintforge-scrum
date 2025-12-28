package com.sprintforge.scrum.board.application.exception;

import com.sprintforge.common.application.exception.DuplicateEntityException;

import java.util.UUID;

public class DuplicateBoardColumnException extends DuplicateEntityException {

    private DuplicateBoardColumnException(String field, String value) {
        super(String.format(
                "Ya existe una columna de tablero con %s \"%s\".",
                field,
                value
        ));
    }

    public static DuplicateBoardColumnException byName(String name) {
        return new DuplicateBoardColumnException("el nombre", name);
    }

    public static DuplicateBoardColumnException bySprintAndName(UUID sprintId, String name) {
        return new DuplicateBoardColumnException(
                "el sprint y nombre",
                String.format("%s / %s", sprintId.toString(), name)
        );
    }
}
