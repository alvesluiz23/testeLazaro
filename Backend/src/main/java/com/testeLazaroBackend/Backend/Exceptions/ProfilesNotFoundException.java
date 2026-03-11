package com.testeLazaroBackend.Backend.Exceptions;

import java.util.List;

public class ProfilesNotFoundException extends RuntimeException {

    public ProfilesNotFoundException(List<Integer> ids) {
        super("Profiles not found for ids: " + ids);
    }

}