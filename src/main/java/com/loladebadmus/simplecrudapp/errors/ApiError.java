package com.loladebadmus.simplecrudapp.errors;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiError {
    private HttpStatus status;
    private List<String> errors;

    public ApiError(HttpStatus status,  List<String> errors) {
        this.status = status;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String error) {
        this.status = status;
        errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "status=" + status +
                ", errors=" + errors +
                '}';
    }
}
