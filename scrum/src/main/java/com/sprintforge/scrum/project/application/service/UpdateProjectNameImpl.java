package com.sprintforge.scrum.project.application.service;

import com.sprintforge.scrum.project.application.exception.DuplicateProjectException;
import com.sprintforge.scrum.project.application.exception.ProjectNotFoundException;
import com.sprintforge.scrum.project.application.port.in.command.UpdateProjectName;
import com.sprintforge.scrum.project.application.port.in.command.UpdateProjectNameCommand;
import com.sprintforge.scrum.project.application.port.out.persistence.ExistsProjectByNameAndIdNot;
import com.sprintforge.scrum.project.application.port.out.persistence.FindProjectById;
import com.sprintforge.scrum.project.application.port.out.persistence.SaveProject;
import com.sprintforge.scrum.project.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateProjectNameImpl implements UpdateProjectName {

    private final FindProjectById findProjectById;
    private final ExistsProjectByNameAndIdNot existsProjectByNameAndIdNot;
    private final SaveProject saveProject;

    @Override
    public Project handle(UpdateProjectNameCommand command) {
        Project project = findProjectById.findById(command.id()).orElseThrow(
                () -> ProjectNotFoundException.byId(command.id())
        );

        if (existsProjectByNameAndIdNot.existsByNameAndIdNot(command.name(), command.id())) {
            throw DuplicateProjectException.byName(command.name());
        }

        project.updateName(command.name());
        Project projectSaved = saveProject.save(project);
        return projectSaved;
    }
}
