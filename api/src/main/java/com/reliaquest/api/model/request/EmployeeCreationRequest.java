package com.reliaquest.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmployeeCreationRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("salary")
    private String salary;

    @JsonProperty("age")
    private String age;

    @JsonProperty("title")
    private String title;

    @JsonProperty("email")
    private String email;
}
