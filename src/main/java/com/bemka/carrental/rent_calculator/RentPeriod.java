package com.bemka.carrental.rent_calculator;

import jakarta.validation.constraints.Pattern;

public record RentPeriod(
        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "The date pattern for start date is YYYY-MM-DD") String startDate,
        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "The date pattern for end date is YYYY-MM-DD") String endDate) {
}
