package com.online.cinema.exception_handlers;

import lombok.Data;

import java.util.Date;

@Data
public class OnlineCinemaError {
    private int status;
    private String message;
    private Date timestamp;

    public OnlineCinemaError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
