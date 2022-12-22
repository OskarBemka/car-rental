package com.bemka.carrental.car.api.dto;

import com.bemka.carrental.car.service.validator.CarBrandType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCarDto(
        @CarBrandType
        String brand,
        @NotBlank(message = "Car model have to contain non-whitespace characters!")
        @Size(max = 64, message = "Car model is too long, max 64 signs!")
        String model
) {
}
