package com.example.viagemdestino.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record TripDto(
        @NotBlank String name,
        @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
        @NotNull Long destinationId
) {
}
