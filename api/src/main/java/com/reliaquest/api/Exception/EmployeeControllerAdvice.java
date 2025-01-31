package com.reliaquest.api.Exception;

import com.reliaquest.api.model.response.ErrorResponse;
import feign.RetryableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeControllerAdvice {

    @ExceptionHandler(exception = BusinessException.class)
    public ErrorResponse getErrorResponseForBusinessClass(BusinessException ex) {
        return ErrorResponse.builder().error(ex.getMessage()).build();
    }

    @ExceptionHandler(RetryableException.class)
    public ErrorResponse getErrorResponse(RetryableException ex) {
        return ErrorResponse.builder().error(ex.getMessage()).build();
    }
}
