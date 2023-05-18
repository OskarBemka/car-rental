package com.bemka.carrental.rent_calculator;

import com.bemka.carrental.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RentCalculatorService {

    private final CarService carService;
    private final RentCalculator rentCalculator;

    public BigDecimal getRentCost(String carExternalId, RentPeriod rentPeriod) {
        final var car = carService.getCar(carExternalId);
        return rentCalculator.calculateRentCost(LocalDate.parse(rentPeriod.startDate()), LocalDate.parse(rentPeriod.endDate()), car);
    }
}
