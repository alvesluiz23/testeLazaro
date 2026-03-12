package com.testeLazaroBackend.Backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProfileDTO(
        @Positive(message = "Profile id must be a positive number")
        int id,
        @NotBlank(message = "Profile description is required")
        @Size(min = 5, message = "Profile description must have at least 5 characters")
        String description
) {}
