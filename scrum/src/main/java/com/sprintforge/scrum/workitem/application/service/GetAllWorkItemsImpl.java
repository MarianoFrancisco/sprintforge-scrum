package com.sprintforge.scrum.workitem.application.service;

import com.sprintforge.scrum.workitem.application.port.in.query.GetAllWorkItems;
import com.sprintforge.scrum.workitem.application.port.in.query.GetAllWorkItemsQuery;
import com.sprintforge.scrum.workitem.application.port.out.persistence.FindAllWorkItems;
import com.sprintforge.scrum.workitem.domain.WorkItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllWorkItemsImpl implements GetAllWorkItems {

    private final FindAllWorkItems findAllWorkItems;

    @Override
    public List<WorkItem> handle(GetAllWorkItemsQuery query) {
        return findAllWorkItems.findAll(query);
    }
}
