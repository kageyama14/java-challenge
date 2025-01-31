package com.reliaquest.api.controller.impl;

import com.reliaquest.api.Exception.BusinessException;
import com.reliaquest.api.controller.IEmployeeController;
import com.reliaquest.api.model.request.EmployeeCreationRequest;
import com.reliaquest.api.model.response.Employee;
import com.reliaquest.api.service.EmployeeService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class EmployeeControllerImpl implements IEmployeeController<Employee, EmployeeCreationRequest> {

    private EmployeeService employeeService;

    public EmployeeControllerImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        if (!employeeList.isEmpty()) return ResponseEntity.ok(employeeList);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        List<Employee> employeeList = employeeService.getAllEmployeeByNameSearch(searchString);
        if (!employeeList.isEmpty()) return ResponseEntity.ok(employeeList);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        Optional<Employee> employeeById = employeeService.findEmployeeById(id);
        return employeeById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent()
                .build());
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        Optional<Integer> highestSalaryOfEmployee = employeeService.getHighestSalaryOfEmployee();
        return highestSalaryOfEmployee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent()
                .build());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<String> topTenHighestEarningEmployeesNames = employeeService.getTopTenHighestEarningEmployeesNames();
        return !topTenHighestEarningEmployeesNames.isEmpty()
                ? ResponseEntity.ok(topTenHighestEarningEmployeesNames)
                : ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Employee> createEmployee(EmployeeCreationRequest employeeInput) throws BusinessException {
        Optional<Employee> employee = employeeService.createEmployee(employeeInput);
        if (employee.isEmpty()) throw new BusinessException("Not able to create employee");
        return ResponseEntity.ok(employee.get());
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) throws BusinessException {
        Optional<String> name = employeeService.deleteEmployeeById(id);
        if (name.isEmpty()) throw new BusinessException("Not able to delete employee");
        return ResponseEntity.ok(name.get());
    }
}
