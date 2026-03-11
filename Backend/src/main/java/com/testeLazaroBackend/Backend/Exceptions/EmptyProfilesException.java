package com.testeLazaroBackend.Backend.Exceptions;

public class EmptyProfilesException extends RuntimeException {
    public EmptyProfilesException() {
        super("Profile list cannot be empty");
    }
}
