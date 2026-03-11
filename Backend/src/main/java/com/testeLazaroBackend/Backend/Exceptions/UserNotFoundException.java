package com.testeLazaroBackend.Backend.Exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(UUID userId) {
        super("User with ID " + userId + " not found");
    }
}
