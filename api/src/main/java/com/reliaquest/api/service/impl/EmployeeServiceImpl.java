package com.reliaquest.api.service.impl;

import com.reliaquest.api.Exception.BusinessException;
import com.reliaquest.api.client.config.EmployeeClient;
import com.reliaquest.api.model.request.EmployeeCreationRequest;
import com.reliaquest.api.model.request.EmployeeDeletionRequest;
import com.reliaquest.api.model.response.Employee;
import com.reliaquest.api.model.response.EmployeeDeletionResponse;
import com.reliaquest.api.model.response.EmployeeListResponse;
import com.reliaquest.api.model.response.EmployeeResponse;
import com.reliaquest.api.service.EmployeeService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeClient employeeClient;

    public EmployeeServiceImpl(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }

    @Override
    public List<Employee> getAllEmployees() {
        log.info("Inside getAllEmployees and fetching all the employees");
        try {
            // Fetching all the employees
            ResponseEntity<EmployeeListResponse> response = employeeClient.getAllEmployees();
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getEmployees();
            }
        } catch (Exception e) {
            log.error("Error while fetching getAllEmployees, due to ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Employee> getAllEmployeeByNameSearch(String searchString) {
        log.info("Inside getAllEmployeeByNameSearch and fetching all the employees with name {}", searchString);
        List<Employee> employeeList = getAllEmployees();
        // Checking if the employee name is present irrespective of the case
        employeeList = employeeList.stream()
                .filter(employee -> employee.getName().toLowerCase().contains(searchString.toLowerCase()))
                .toList();
        if (!employeeList.isEmpty()) log.error("Didn't find any employees with the name {}", searchString);
        return employeeList;
    }

    @Override
    public Optional<Employee> findEmployeeById(String id) {
        log.info("Inside findEmployeeById and fetching the employees with id {}", id);
        try {
            // Checking for employee by given id
            ResponseEntity<EmployeeResponse> response = employeeClient.findEmployeeById(id);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Optional.of(response.getBody().getEmployee());
            }

            if (response.getStatusCode().is4xxClientError()) {
                log.info("No employee found with the id {}", id);
            }
        } catch (Exception e) {
            log.error("Didn't find any employees with the id {} ", id, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> getHighestSalaryOfEmployee() {
        log.info("Inside getHighestSalaryOfEmployee and fetching the highest salary");
        int maxSalary = 0;
        // Getting all the employees
        List<Employee> employeeList = getAllEmployees();
        // Finding the max salary linearly
        for (Employee employee : employeeList) {
            maxSalary = Math.max(maxSalary, Integer.parseInt(employee.getSalary()));
        }
        return !employeeList.isEmpty() ? Optional.of(maxSalary) : Optional.empty();
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeesNames() {
        log.info(
                "Inside getTopTenHighestEarningEmployeesNames and fetching the names of first 10 employees with highest employees");
        // Getting all the employees
        List<Employee> employeeList = getAllEmployees();

        // Sorting the employees based on salary
        employeeList.sort(new Employee.EmployeeSalaryComparator());

        // Getting the list of first 10 employees in the order of descending salary
        return employeeList.stream().map(Employee::getName).limit(10).toList();
    }

    @Override
    public Optional<Employee> createEmployee(EmployeeCreationRequest employee) {
        log.info("Creating an Employee");
        try {
            // Sending request to server of creating an employee
            ResponseEntity<EmployeeResponse> employeeCreationResponse = employeeClient.saveEmployee(employee);
            if (employeeCreationResponse.getStatusCode().is2xxSuccessful()
                    && employeeCreationResponse.getBody() != null) {
                log.info("Successfully created an employee with name: {}", employee.getName());
                return Optional.of(employeeCreationResponse.getBody().getEmployee());
            }
        } catch (Exception e) {
            log.error("Error while creating an employee ", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> deleteEmployeeById(String id) {
        log.info("Deleting an employee");
        try {
            // Finding employee to get the name
            Optional<Employee> employee = findEmployeeById(id);
            if (employee.isEmpty()) throw new BusinessException("Unable to find the Employee with id: " + id);

            // Creating json body for delete server request
            EmployeeDeletionRequest employeeDeletionRequest = EmployeeDeletionRequest.builder()
                    .name(employee.get().getName())
                    .build();

            // Calling delete api from the server
            ResponseEntity<EmployeeDeletionResponse> employeeDeletionResponse =
                    employeeClient.deleteEmployee(employeeDeletionRequest);
            if (employeeDeletionResponse.getBody() != null
                    && employeeDeletionResponse.getBody().isData()) {
                log.info("Successfully deleted the employee with id {}", id);
                return Optional.of(employee.get().getName());
            }
        } catch (Exception e) {
            log.error("Error while deleting");
        }
        return Optional.empty();
    }
}
