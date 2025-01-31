package com.reliaquest.api.service;

import com.reliaquest.api.model.request.EmployeeCreationRequest;
import com.reliaquest.api.model.response.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    List<Employee> getAllEmployeeByNameSearch(String searchString);

    Optional<Employee> findEmployeeById(String id);

    Optional<Integer> getHighestSalaryOfEmployee();

    List<String> getTopTenHighestEarningEmployeesNames();

    Optional<Employee> createEmployee(EmployeeCreationRequest employee);

    Optional<String> deleteEmployeeById(String id);
}
