package com.bemka.carrental.rent_price.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DayOfWeek;
import java.util.stream.Stream;

public class DayOfWeekValidator implements ConstraintValidator<DayOfWeekType, String> {
    @Override
    public void initialize(DayOfWeekType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        return Stream.of(DayOfWeek.values()).anyMatch(v -> v.toString().equalsIgnoreCase(value));
    }
}
