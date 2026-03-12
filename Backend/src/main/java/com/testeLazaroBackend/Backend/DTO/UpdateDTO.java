package com.testeLazaroBackend.Backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateDTO(
        @NotBlank(message = "Description is required")
        @Size(min = 5, message = "Description must have at least 5 characters")
        String description
) {
}
