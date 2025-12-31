package com.sprintforge.scrum.project.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprintforge.scrum.project.application.port.in.query.GetActiveProjectsByEmployeeId;
import com.sprintforge.scrum.project.application.port.out.persistence.FindActiveProjectsByEmployeeId;
import com.sprintforge.scrum.project.domain.Project;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetActiveProjectsByEmployeeIdImpl implements GetActiveProjectsByEmployeeId{
    private final FindActiveProjectsByEmployeeId findActiveProjectsByEmployeeId;

    @Override
    public List<Project> handle(UUID employeeId) {
        return findActiveProjectsByEmployeeId.findActiveByEmployeeId(employeeId);
    }
}
