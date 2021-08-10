package com.loladebadmus.simplecrudapp.errors;

public class FailedRegistrationException extends RuntimeException{

    public FailedRegistrationException(String message) {
        super(message);
    }
}
