package com.testeLazaroBackend.Backend.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record UserDTO(
        UUID id,
        @NotBlank(message = "User name is required")
        @Size(min = 10, message = "User name must have at least 10 characters")
        String name,
        @NotNull(message = "Profiles list is required")
        @Size(min = 1, message = "At least one profile must be provided")
        List<@Valid ProfileDTO> profiles
) {}
