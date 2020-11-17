package com.rezdy.lunch.controller.exception;

import java.util.Collections;
import java.util.List;

public class ErrorResponse {

    private int status;
    private String errorMessage;
    private List<ValidationFieldError> fieldErrors = Collections.emptyList();

    public int getStatus() {
        return status;
    }

    public ErrorResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ErrorResponse setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public List<ValidationFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public ErrorResponse setFieldErrors(List<ValidationFieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
        return this;
    }
}
