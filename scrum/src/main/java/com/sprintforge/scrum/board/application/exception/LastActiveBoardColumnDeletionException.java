package com.sprintforge.scrum.board.application.exception;

import com.sprintforge.common.application.exception.BusinessException;

public class LastActiveBoardColumnDeletionException extends BusinessException {

    private LastActiveBoardColumnDeletionException() {
        super("No se puede eliminar la columna porque es la última columna activa del tablero.");
    }

    public static LastActiveBoardColumnDeletionException create() {
        return new LastActiveBoardColumnDeletionException();
    }
}
