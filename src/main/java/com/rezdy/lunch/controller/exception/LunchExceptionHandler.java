package com.rezdy.lunch.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class LunchExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(LunchExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        log.error("Unhandled exception", e);
        return new ErrorResponse().setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).setErrorMessage("Sorry, there was an unhandled exception.");
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleBindException(BindException e) {
        return new ErrorResponse().setStatus(HttpStatus.BAD_REQUEST.value()).setErrorMessage("Validation error").setFieldErrors(toErrorList(e.getFieldErrors()));
    }

    private List<ValidationFieldError> toErrorList(List<FieldError> fieldErrors) {
        return fieldErrors.stream().map(error -> new ValidationFieldError(error.getField(), error.getDefaultMessage())).collect(Collectors.toList());
    }
}
