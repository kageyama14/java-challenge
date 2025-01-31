package com.reliaquest.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeResponse extends BaseResponse {

    @JsonProperty("data")
    public Employee employee;
}
