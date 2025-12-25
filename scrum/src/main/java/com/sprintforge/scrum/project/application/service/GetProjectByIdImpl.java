package com.sprintforge.scrum.project.application.service;

import com.sprintforge.scrum.project.application.exception.ProjectNotFoundException;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectById;
import com.sprintforge.scrum.project.application.port.in.query.GetProjectByIdQuery;
import com.sprintforge.scrum.project.application.port.out.persistence.FindProjectById;
import com.sprintforge.scrum.project.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetProjectByIdImpl implements GetProjectById {

    private final FindProjectById findProjectById;

    @Override
    public Project handle(GetProjectByIdQuery query) {
        return findProjectById.findById(query.id()).orElseThrow(
                () -> ProjectNotFoundException.byId(query.id())
        );
    }
}
