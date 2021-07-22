package com.loladebadmus.simplecrudapp.errors;

public class DuplicateDataException extends RuntimeException{
    public DuplicateDataException(String dataField, String input) {
        super(String.format("The %s \" %s \" is already taken", dataField, input));
    }
}
