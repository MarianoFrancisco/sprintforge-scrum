package com.sprintforge.scrum.board.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

import java.util.UUID;

public class InvalidTargetBoardColumnException extends BusinessException {

    private InvalidTargetBoardColumnException(String message) {
        super(message);
    }

    public static InvalidTargetBoardColumnException sameAsDeleted(UUID id) {
        return new InvalidTargetBoardColumnException(
                "La columna destino no puede ser la misma que la columna eliminada. Id: " + id
        );
    }

    public static InvalidTargetBoardColumnException differentSprint(UUID targetSprintId) {
        return new InvalidTargetBoardColumnException(
                "La columna destino no pertenece al mismo sprint. Sprint destino: " + targetSprintId
        );
    }

    public static InvalidTargetBoardColumnException deleted(UUID id) {
        return new InvalidTargetBoardColumnException(
                "La columna destino está eliminada. Id: " + id
        );
    }
}
