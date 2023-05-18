package com.bemka.carrental.rent_calculator;

import com.bemka.carrental.common.DatesValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/car-rental/rent-calculator")
public class RentCalculatorController {

    private final RentCalculatorService rentCalculatorService;
    private final DatesValidator datesValidator;

    @PostMapping("{carExternalId}")
    public BigDecimal calculateRentCost(@PathVariable String carExternalId, @Valid @RequestBody RentPeriod rentPeriod) {
        datesValidator.validateDates(LocalDate.parse(rentPeriod.startDate()), LocalDate.parse(rentPeriod.endDate()));
        return rentCalculatorService.getRentCost(carExternalId, rentPeriod);
    }
}
