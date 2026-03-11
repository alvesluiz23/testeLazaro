package com.testeLazaroBackend.Backend.DTO;

import java.util.List;

public record ReturnProfileDTO (
        Integer id,
        String description,
        List<ReturnUserDTO> users
) {}