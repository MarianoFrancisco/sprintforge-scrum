package com.sprintforge.scrum.project.application.port.in.command;

public interface DeleteProject {
    void handle(DeleteProjectCommand command);
}