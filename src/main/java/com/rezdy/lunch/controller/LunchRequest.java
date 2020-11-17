package com.rezdy.lunch.controller;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class LunchRequest {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Date is required")
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
