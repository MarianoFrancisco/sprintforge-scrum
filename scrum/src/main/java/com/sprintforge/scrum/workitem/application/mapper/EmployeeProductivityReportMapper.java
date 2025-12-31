package com.sprintforge.scrum.workitem.application.mapper;

import com.sprintforge.common.application.port.result.EmployeeProductivityItem;
import com.sprintforge.common.application.port.result.EmployeeProductivityReportResult;
import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.workitem.application.port.out.persistence.raw.EmployeeProductivityRawItem;
import com.sprintforge.scrum.workitem.application.port.out.persistence.raw.EmployeeProductivityReportRaw;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class EmployeeProductivityReportMapper {

    public EmployeeProductivityReportResult toResult(
            EmployeeProductivityReportRaw raw,
            Map<UUID, EmployeeResult> employeeById
    ) {
        List<EmployeeProductivityItem> employees = mapEmployees(raw.employees(), employeeById);

        return new EmployeeProductivityReportResult(
                raw.from(),
                raw.to(),
                employees,
                raw.totalEmployees()
        );
    }

    private List<EmployeeProductivityItem> mapEmployees(
            List<EmployeeProductivityRawItem> raws,
            Map<UUID, EmployeeResult> employeeById
    ) {
        if (raws == null || raws.isEmpty()) return List.of();
        if (employeeById == null || employeeById.isEmpty()) return List.of();

        List<EmployeeProductivityItem> items = new ArrayList<>();

        for (EmployeeProductivityRawItem r : raws) {
            EmployeeResult employee = employeeById.get(r.employeeId());
            if (employee == null) continue;

            items.add(new EmployeeProductivityItem(
                    employee,
                    r.workedStories(),
                    r.completedStories(),
                    r.pendingStories(),
                    r.deliveredStoryPoints()
            ));
        }

        return items;
    }
}
