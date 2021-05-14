package com.loladebadmus.simplecrudapp.errors;

import java.util.UUID;

public class IDNotFoundException extends RuntimeException{


    public IDNotFoundException(UUID userId) {
        super(String.format("User with UUID %s not found", userId));
    }

    public IDNotFoundException(String objectName, Long userId) {
        super(String.format("%s with id %d not found", objectName, userId));
    }
}
