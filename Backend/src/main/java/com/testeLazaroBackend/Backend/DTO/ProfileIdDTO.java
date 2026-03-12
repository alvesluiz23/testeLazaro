package com.testeLazaroBackend.Backend.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProfileIdDTO(
        @Positive(message = "Profile id must be a positive number")
        int id
) {
}

