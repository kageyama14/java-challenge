package com.reliaquest.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeListResponse extends BaseResponse {

    @JsonProperty("data")
    public List<Employee> employees;
}
