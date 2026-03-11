package com.testeLazaroBackend.Backend.DTO;

import java.util.List;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String name,
        List<Integer> profileIds
) {}