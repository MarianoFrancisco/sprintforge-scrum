package com.sprintforge.scrum.board.application.service;

import com.sprintforge.scrum.board.application.port.in.query.GetAllBoardColumnsBySprintId;
import com.sprintforge.scrum.board.application.port.in.query.GetAllBoardColumnsBySprintIdQuery;
import com.sprintforge.scrum.board.application.port.out.persistence.FindAllBoardColumnsBySprintId;
import com.sprintforge.scrum.board.domain.BoardColumn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllBoardColumnsBySprintIdImpl implements GetAllBoardColumnsBySprintId {

    private final FindAllBoardColumnsBySprintId findAllBoardColumnsBySprintId;

    @Override
    public List<BoardColumn> handle(GetAllBoardColumnsBySprintIdQuery query) {
        return findAllBoardColumnsBySprintId.findAllBySprintId(query);
    }
}
