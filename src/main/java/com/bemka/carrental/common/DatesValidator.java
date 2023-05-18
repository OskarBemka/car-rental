package com.bemka.carrental.common;

import com.bemka.carrental.common.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatesValidator {

    public void validateDates(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new BadRequestException("The start date can not be after end date!");
        }
    }
}
