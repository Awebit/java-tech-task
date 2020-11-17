package com.rezdy.lunch.controller.exception;

public class ValidationFieldError {
    private final String field;
    private final String error;

    public ValidationFieldError(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
}
