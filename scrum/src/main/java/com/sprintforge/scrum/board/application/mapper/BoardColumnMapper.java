package com.sprintforge.scrum.board.application.mapper;

import com.sprintforge.scrum.board.application.port.in.command.CreateBoardColumnCommand;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BoardColumnMapper {

    public BoardColumn toDomain(
            CreateBoardColumnCommand command,
            int position,
            Sprint sprint
    ) {
        if (command == null) {
            return null;
        }

        return new BoardColumn(
                command.name(),
                position,
                sprint
        );
    }

    public BoardColumn toDefaultDomain(
            String name,
            int position,
            boolean isFinal,
            Sprint sprint
    ) {
        BoardColumn column = new BoardColumn(
                name,
                position,
                sprint
        );

        if (isFinal) {
            column.markAsFinal();
        }

        return column;
    }
}
