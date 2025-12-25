package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.workitem.application.exception.WorkItemNotFoundException;
import com.sprintforge.scrum.workitem.application.port.in.query.GetWorkItemById;
import com.sprintforge.scrum.workitem.application.port.in.query.GetWorkItemByIdQuery;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindWorkItemById;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetWorkItemByIdImpl implements GetWorkItemById {

    private final FindWorkItemById findWorkItemById;

    @Override
    public WorkItem handle(GetWorkItemByIdQuery query) {
        return findWorkItemById.findById(query.id()).orElseThrow(
                () -> WorkItemNotFoundException.byId(query.id())
        );
    }
}
