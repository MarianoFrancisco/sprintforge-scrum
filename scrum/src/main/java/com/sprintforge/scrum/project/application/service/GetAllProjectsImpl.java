package com.sprintforge.scrum.project.application.service;

import com.sprintforge.scrum.project.application.port.in.query.GetAllProjects;
import com.sprintforge.scrum.project.application.port.in.query.GetAllProjectsQuery;
import com.sprintforge.scrum.project.application.port.out.persistence.FindAllProjects;
import com.sprintforge.scrum.project.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllProjectsImpl implements GetAllProjects {

    private final FindAllProjects findAllProjects;

    @Override
    public List<Project> handle(GetAllProjectsQuery query) {
        return findAllProjects.findAll(query);
    }
}
