package com.testeLazaroBackend.Backend.Exceptions;

public class ProfileDescriptionTooShortException  extends RuntimeException{
    public ProfileDescriptionTooShortException(int minLength) {
        super("Profile description must have at least " + minLength + " characters");
    }
}
