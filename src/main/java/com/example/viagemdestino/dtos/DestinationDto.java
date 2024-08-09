package com.example.viagemdestino.dtos;

import jakarta.validation.constraints.NotBlank;

public record DestinationDto(@NotBlank String country, @NotBlank String name) {
}
