package com.bemka.carrental.rent_price.api.dto;

import com.bemka.carrental.rent_price.service.validator.DayOfWeekType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

public record RentPriceDto (@DayOfWeekType String dayOfWeek,
                            @DecimalMin(value = "0.00", inclusive = false, message = "The price must be greater than 0.00!")
                            @Digits(integer = 4, fraction = 2, message = "The expected price format is <4 digits>.<2 digits>!")
                            BigDecimal price){
}
