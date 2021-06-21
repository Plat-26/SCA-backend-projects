package com.loladebadmus.simplecrudapp.errors;

public class FailedRegistrationException extends RuntimeException{

    //todo: handle exception globally
    public FailedRegistrationException(String message) {
        super(message);
    }
}
