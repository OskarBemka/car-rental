package com.bemka.carrental.rent_calculator;

import com.bemka.carrental.car.model.Car;
import com.bemka.carrental.common.exception.NotFoundException;
import com.bemka.carrental.rent_price.model.RentPrice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
public class RentCalculator {

    public BigDecimal calculateRentCost(LocalDate startDate, LocalDate endDate, Car car) {
        var daysOfWeek = startDate.datesUntil(endDate.plusDays(1)).map(LocalDate::getDayOfWeek).toList();
        BigDecimal rentCost = BigDecimal.valueOf(0);
        for (DayOfWeek dayOfWeek : daysOfWeek) {
            rentCost = rentCost.add(getPriceForDay(dayOfWeek, car));
        }
        return rentCost;
    }

    private BigDecimal getPriceForDay(DayOfWeek checkDay, Car car) {
       return car.getRentPrices().stream().filter(e -> e.getDayOfWeek().equals(checkDay)).toList()
               .stream().map(RentPrice::getPrice)
               .findFirst()
               .orElseThrow(() -> new NotFoundException(String.format("The %s rent price for car with external id = %s not found!", checkDay, car.getExternalId())));
    }
}
