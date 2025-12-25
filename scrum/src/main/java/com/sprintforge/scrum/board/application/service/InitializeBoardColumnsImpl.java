package com.sprintforge.scrum.board.application.service;

import com.sprintforge.scrum.board.application.mapper.BoardColumnMapper;
import com.sprintforge.scrum.board.application.port.in.command.InitializeBoardColumns;
import com.sprintforge.scrum.board.application.port.in.command.InitializeBoardColumnsCommand;
import com.sprintforge.scrum.board.application.port.out.persistence.SaveAllBoardColumn;
import com.sprintforge.scrum.board.domain.BoardColumn;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintById;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintByIdQuery;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class InitializeBoardColumnsImpl implements InitializeBoardColumns {

    private final GetSprintById getSprintById;
    private final SaveAllBoardColumn save;

    @Override
    public void handle(InitializeBoardColumnsCommand command) {
        Sprint sprint = getSprintById.handle(
                new GetSprintByIdQuery(command.sprintId())
        );

        BoardColumn pendientes = BoardColumnMapper.toDefaultDomain(
                "Pendientes",
                0,
                false,
                sprint
        );

        BoardColumn enDesarrollo = BoardColumnMapper.toDefaultDomain(
                "En desarrollo",
                1,
                false,
                sprint
        );

        BoardColumn finalizadas = BoardColumnMapper.toDefaultDomain(
                "Finalizadas",
                2,
                true,
                sprint
        );

        save.saveAll(
                List.of(pendientes, enDesarrollo, finalizadas)
        );
    }
}
