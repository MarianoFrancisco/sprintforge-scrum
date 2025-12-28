package com.sprintforge.scrum.board.application.service;

import com.sprintforge.scrum.board.application.exception.BoardColumnNotFoundException;
import com.sprintforge.scrum.board.application.port.in.query.GetDefaultBoardColumnBySprint;
import com.sprintforge.scrum.board.application.port.in.query.GetDefaultBoardColumnBySprintQuery;
import com.sprintforge.scrum.board.application.port.out.persistence.FindDefaultBoardColumnBySprintId;
import com.sprintforge.scrum.board.domain.BoardColumn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetDefaultBoardColumnBySprintImpl implements GetDefaultBoardColumnBySprint {

    private final FindDefaultBoardColumnBySprintId findDefaultBoardColumnBySprintId;

    @Override
    public BoardColumn handle(GetDefaultBoardColumnBySprintQuery query) {
        return findDefaultBoardColumnBySprintId.findDefaultBySprintId(query.sprintId())
                .orElseThrow(() -> BoardColumnNotFoundException.defaultColumnNotFound(query.sprintId()));
    }
}
