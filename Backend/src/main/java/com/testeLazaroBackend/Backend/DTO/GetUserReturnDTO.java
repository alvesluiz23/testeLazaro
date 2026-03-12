package com.testeLazaroBackend.Backend.DTO;

import java.util.List;
import java.util.UUID;

public record GetUserReturnDTO(
        UUID id,
        String name,
        List<ProfileDTO> profiles
) {
}
