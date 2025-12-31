package com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence;

import com.sprintforge.scrum.workitem.application.port.in.query.GetEmployeeProductivityReportQuery;
import com.sprintforge.scrum.workitem.application.port.out.persistence.LoadEmployeeProductivityReportRaw;
import com.sprintforge.scrum.workitem.application.port.out.persistence.raw.EmployeeProductivityRawItem;
import com.sprintforge.scrum.workitem.application.port.out.persistence.raw.EmployeeProductivityReportRaw;
import com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.projection.EmployeeProductivityReportView;
import com.sprintforge.scrum.workitem.infrastructure.adapter.out.persistence.repository.WorkItemReportJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.time.ZoneOffset.UTC;

@Repository
@RequiredArgsConstructor
public class WorkItemReportRepository implements
        LoadEmployeeProductivityReportRaw {

    private final WorkItemReportJpaRepository workItemReportJpaRepository;

    @Override
    public EmployeeProductivityReportRaw load(GetEmployeeProductivityReportQuery query) {
        Instant fromInstant = query.from() != null
                ? query.from().atStartOfDay(UTC).toInstant()
                : Instant.EPOCH;

        Instant toInstant = query.to() != null
                ? query.to().plusDays(1).atStartOfDay(UTC).toInstant()
                : Instant.now();

        List<EmployeeProductivityReportView> rows =
                workItemReportJpaRepository.findEmployeeProductivityReport(
                        fromInstant,
                        toInstant,
                        query.employeeId()
                );

        List<EmployeeProductivityRawItem> mapped = rows.stream()
                .map(r -> new EmployeeProductivityRawItem(
                        r.getEmployeeId(),
                        safeLong(r.getWorkedStories()),
                        safeLong(r.getCompletedStories()),
                        safeLong(r.getPendingStories()),
                        safeLong(r.getDeliveredStoryPoints())
                ))
                .toList();

        return new EmployeeProductivityReportRaw(
                query.from(),
                query.to(),
                mapped,
                mapped.size()
        );
    }

    private static long safeLong(Long value) {
        return value == null ? 0L : value;
    }
}
