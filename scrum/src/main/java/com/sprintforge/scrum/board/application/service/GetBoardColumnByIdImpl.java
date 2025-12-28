package com.sprintforge.scrum.board.application.service;

import com.sprintforge.scrum.board.application.exception.BoardColumnNotFoundException;
import com.sprintforge.scrum.board.application.port.in.query.GetBoardColumnById;
import com.sprintforge.scrum.board.application.port.in.query.GetBoardColumnByIdQuery;
import com.sprintforge.scrum.board.application.port.out.persistence.FindBoardColumnById;
import com.sprintforge.scrum.board.domain.BoardColumn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetBoardColumnByIdImpl implements GetBoardColumnById {

    private final FindBoardColumnById findBoardColumnById;

    @Override
    public BoardColumn handle(GetBoardColumnByIdQuery query) {
        return findBoardColumnById.findById(query.id())
                .orElseThrow(() ->
                        BoardColumnNotFoundException.byId(query.id())
                );
    }
}
