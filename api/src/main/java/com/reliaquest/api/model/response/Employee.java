package com.reliaquest.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Comparator;
import lombok.Data;

@Data
public class Employee {

    @JsonProperty("id")
    private String id;

    @JsonProperty("employee_name")
    private String name;

    @JsonProperty("employee_salary")
    private String salary;

    @JsonProperty("employee_age")
    private String age;

    @JsonProperty("employee_title")
    private String title;

    @JsonProperty("employee_email")
    private String email;

    public static class EmployeeSalaryComparator implements Comparator<Employee> {

        @Override
        public int compare(Employee e1, Employee e2) {
            return Integer.parseInt(e2.getSalary()) - Integer.parseInt(e1.getSalary());
        }
    }
}
