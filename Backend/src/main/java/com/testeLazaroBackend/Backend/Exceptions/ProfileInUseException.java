package com.testeLazaroBackend.Backend.Exceptions;

public class ProfileInUseException extends RuntimeException {
    public ProfileInUseException(Integer id) {
        super("Cannot delete profile with id " + id + " because it is associated with one or more users.");
    }
}
