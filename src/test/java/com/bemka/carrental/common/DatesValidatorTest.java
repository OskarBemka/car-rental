package com.bemka.carrental.common;

import com.bemka.carrental.common.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DatesValidatorTest {

    private DatesValidator datesValidator;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    public void init() {
        datesValidator = new DatesValidator();
    }

    @Test
    public void shouldDoNothing_when_startDateIsNotAfterEndDate() {
        //arrange
        startDate = LocalDate.of(2023, 1, 1);
        endDate = LocalDate.of(2023, 1, 10);

        //act
        //assert
        datesValidator.validateDates(startDate, endDate);
    }

    @Test
    public void shouldThrowException_when_startDateIsAfterEndDate() {
        //arrange
        startDate = LocalDate.of(2023, 1, 10);
        endDate = LocalDate.of(2023, 1, 1);

        //act
        //assert
        assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> datesValidator.validateDates(startDate, endDate))
                .withMessage("The start date can not be after end date!");
    }
}
