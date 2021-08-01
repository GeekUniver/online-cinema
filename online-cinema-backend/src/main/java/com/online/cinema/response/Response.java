package com.online.cinema.response;

import java.util.List;

public class Response<T> {
    private T data;
    private List<String> errors;

    public Response(T data) {
        this.data = data;
    }

    public Response(List<String> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
