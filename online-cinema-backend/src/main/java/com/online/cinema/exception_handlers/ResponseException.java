package com.online.cinema.exception_handlers;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseException {
    private int status;
    private String message;
    private Date timestamp;

    public ResponseException(int status, String message, Date timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ResponseException(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
