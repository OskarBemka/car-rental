package com.bemka.carrental.car.service.validator;

import com.bemka.carrental.car.model.CarBrand;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.stream.Stream;

public class CarBrandValidator implements ConstraintValidator<CarBrandType, String> {

    @Override
    public void initialize(CarBrandType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return Stream.of(CarBrand.values()).anyMatch(v -> v.toString().equalsIgnoreCase(value));
    }
}
