package com.reliaquest.api.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDeletionRequest {

    @JsonProperty("name")
    private String name;
}
