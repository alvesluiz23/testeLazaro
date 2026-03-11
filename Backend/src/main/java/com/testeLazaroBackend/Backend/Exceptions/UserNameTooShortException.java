package com.testeLazaroBackend.Backend.Exceptions;

public class UserNameTooShortException extends RuntimeException {
    public UserNameTooShortException(int minLength) {
        super("User name must have at least " + minLength + " characters");
    }

}
