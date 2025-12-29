package com.sprintforge.scrum.common.infrastructure.adapter.out.rest.employee;

import com.sprintforge.common.application.exception.BusinessException;
import com.sprintforge.common.application.port.result.EmployeeResult;
import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployees;
import com.sprintforge.scrum.common.application.port.out.rest.employee.command.ValidateEmployeesCommand;
import com.sprintforge.scrum.common.application.port.out.rest.employee.query.GetEmployeesByIds;
import com.sprintforge.scrum.common.application.port.out.rest.employee.query.GetEmployeesByIdsQuery;
import com.sprintforge.scrum.common.infrastructure.adapter.in.rest.dto.EmployeeResponseDTO;
import com.sprintforge.scrum.common.infrastructure.adapter.out.rest.employee.dto.GetEmployeesByIdsRequestDTO;
import com.sprintforge.scrum.common.infrastructure.adapter.out.rest.employee.mapper.EmployeeResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

import static com.sprintforge.common.infrastructure.rest.error.RemoteProblemDetailExtractor.extractDetailOrRaw;

@Component
@RequiredArgsConstructor
public class EmployeeApi implements GetEmployeesByIds, ValidateEmployees {

    private static final String INTERNAL_EMPLOYEES_BASE_PATH =
            "/internal-api/v1/employee";

    private static final String VALIDATE_IDS_PATH =
            INTERNAL_EMPLOYEES_BASE_PATH + "/validate-ids";

    private static final String GET_BY_IDS_PATH =
            INTERNAL_EMPLOYEES_BASE_PATH + "/get-by-ids";

    private final RestClient employeeRestClient;

    @Override
    public void validate(ValidateEmployeesCommand command) {
        GetEmployeesByIdsRequestDTO requestDTO =
                new GetEmployeesByIdsRequestDTO(command.employeeIds());

        try {
            employeeRestClient.post()
                    .uri(VALIDATE_IDS_PATH)
                    .body(requestDTO)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.Conflict ex) {
            throw new BusinessException(extractDetailOrRaw(ex.getResponseBodyAsString()));
        }
    }

    @Override
    public List<EmployeeResult> getByIds(GetEmployeesByIdsQuery query) {
        GetEmployeesByIdsRequestDTO requestDTO =
                new GetEmployeesByIdsRequestDTO(query.employeeIds());
        try {
            List<EmployeeResponseDTO> response = employeeRestClient.post()
                    .uri(GET_BY_IDS_PATH)
                    .body(requestDTO)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return response == null
                    ? List.of()
                    : response.stream()
                    .map(EmployeeResultMapper::toResult)
                    .toList();
        } catch (HttpClientErrorException.Conflict ex) {
            throw new BusinessException(extractDetailOrRaw(ex.getResponseBodyAsString()));
        }
    }
}
