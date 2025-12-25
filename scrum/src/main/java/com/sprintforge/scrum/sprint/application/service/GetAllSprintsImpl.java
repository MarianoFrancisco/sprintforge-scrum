package com.sprintforge.scrum.sprint.application.service;

import com.sprintforge.scrum.sprint.application.port.in.query.GetAllSprints;
import com.sprintforge.scrum.sprint.application.port.in.query.GetAllSprintsQuery;
import com.sprintforge.scrum.sprint.application.port.out.persistence.FindAllSprints;
import com.sprintforge.scrum.sprint.domain.Sprint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllSprintsImpl implements GetAllSprints {

    private final FindAllSprints findAllSprints;

    @Override
    public List<Sprint> handle(GetAllSprintsQuery query) {
        return findAllSprints.findAll(query);
    }
}
