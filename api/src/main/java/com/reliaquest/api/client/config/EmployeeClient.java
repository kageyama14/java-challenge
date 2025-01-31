package com.reliaquest.api.client.config;

import com.reliaquest.api.model.request.EmployeeCreationRequest;
import com.reliaquest.api.model.request.EmployeeDeletionRequest;
import com.reliaquest.api.model.response.EmployeeDeletionResponse;
import com.reliaquest.api.model.response.EmployeeListResponse;
import com.reliaquest.api.model.response.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "employee-service", url = "${employee.service.url}", configuration = ClientConfiguration.class)
public interface EmployeeClient {

    @GetMapping(produces = "application/json")
    ResponseEntity<EmployeeListResponse> getAllEmployees();

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<EmployeeResponse> findEmployeeById(@PathVariable("id") String id);

    @PostMapping(produces = "application/json")
    ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody EmployeeCreationRequest employee);

    @DeleteMapping(produces = "application/json")
    ResponseEntity<EmployeeDeletionResponse> deleteEmployee(@RequestBody EmployeeDeletionRequest request);
}
