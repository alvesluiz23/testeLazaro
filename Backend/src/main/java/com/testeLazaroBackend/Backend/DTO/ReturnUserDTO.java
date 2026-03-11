package com.testeLazaroBackend.Backend.DTO;

import java.util.UUID;

public record ReturnUserDTO(
        UUID id,
        String name
) {}
