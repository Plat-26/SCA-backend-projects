package com.loladebadmus.simplecrudapp.errors;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException{


    public ResourceNotFoundException(UUID userId) {
        super(String.format("User with UUID %s not found", userId));
    }

    public ResourceNotFoundException(String objectName, Long userId) {
        super(String.format("%s with id %d not found", objectName, userId));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
