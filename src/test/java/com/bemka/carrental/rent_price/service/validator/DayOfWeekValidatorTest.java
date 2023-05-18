package com.bemka.carrental.rent_price.service.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class DayOfWeekValidatorTest {

    private DayOfWeekValidator dayOfWeekValidator;

    @BeforeEach
    public void init() {
        dayOfWeekValidator = new DayOfWeekValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"monday", "Monday", "MONDAY", "tuesday", "Tuesday", "TUESDAY", "wednesday", "Wednesday", "WEDNESDAY",
            "thursday", "Thursday", "THURSDAY", "friday", "Friday", "FRIDAY", "saturday", "Saturday", "Saturday", "sunday", "Sunday", "SUNDAY"})
    public void shouldReturnTrue_when_dayOfWeekIsValid(String dayOfWeek) {
        //arrange
        //act
        final var result = dayOfWeekValidator.isValid(dayOfWeek, null);
        //assert
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalse_when_dayOfWeekIsNotValid() {
        //arrange
        final var dayOfWeek = "wrong day of week";
        //act
        final var result = dayOfWeekValidator.isValid(dayOfWeek, null);
        //assert
        assertThat(result).isFalse();
    }
}
