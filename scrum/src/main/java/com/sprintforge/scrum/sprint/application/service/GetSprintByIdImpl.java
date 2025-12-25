package com.sprintforge.scrum.sprint.application.service;

import com.sprintforge.scrum.sprint.application.exception.SprintNotFoundException;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintById;
import com.sprintforge.scrum.sprint.application.port.in.query.GetSprintByIdQuery;
import com.sprintforge.scrum.sprint.application.port.out.persistence.FindSprintById;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetSprintByIdImpl implements GetSprintById {

    private final FindSprintById findSprintById;

    @Override
    public Sprint handle(GetSprintByIdQuery query) {
        return findSprintById.findById(query.id()).orElseThrow(
                () -> SprintNotFoundException.byId(query.id())
        );
    }
}
