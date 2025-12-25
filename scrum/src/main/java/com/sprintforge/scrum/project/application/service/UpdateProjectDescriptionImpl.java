package com.sprintforge.scrum.project.application.service;

import com.sprintforge.scrum.project.application.exception.ProjectNotFoundException;
import com.sprintforge.scrum.project.application.port.in.command.UpdateProjectDescription;
import com.sprintforge.scrum.project.application.port.in.command.UpdateProjectDescriptionCommand;
import com.sprintforge.scrum.project.application.port.out.persistence.FindProjectById;
import com.sprintforge.scrum.project.application.port.out.persistence.SaveProject;
import com.sprintforge.scrum.project.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UpdateProjectDescriptionImpl implements UpdateProjectDescription {

    private final FindProjectById findProjectById;
    private final SaveProject saveProject;

    @Override
    public Project handle(UpdateProjectDescriptionCommand command) {
        Project project = findProjectById.findById(command.id()).orElseThrow(
                () -> ProjectNotFoundException.byId(command.id())
        );
        project.updateDescription(command.description());
        Project activatedProject = saveProject.save(project);
        return activatedProject;
    }
}
