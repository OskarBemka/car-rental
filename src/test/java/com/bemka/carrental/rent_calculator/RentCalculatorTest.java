package com.bemka.carrental.rent_calculator;

import com.bemka.carrental.car.model.Car;
import com.bemka.carrental.common.exception.NotFoundException;
import com.bemka.carrental.rent_price.model.RentPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RentCalculatorTest {

    private RentCalculator rentCalculator;
    private RentPrice mondayRentPrice;
    private RentPrice tuesdayRentPrice;
    private RentPrice wednesdayRentPrice;
    private RentPrice thursdayRentPrice;
    private RentPrice fridayRentPrice;
    private RentPrice saturdayRentPrice;
    private RentPrice sundayRentPrice;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    public void init() {
        rentCalculator = new RentCalculator();
        car = new Car();
        mondayRentPrice = new RentPrice();
        mondayRentPrice.setDayOfWeek(DayOfWeek.MONDAY);
        mondayRentPrice.setPrice(BigDecimal.valueOf(1));
        mondayRentPrice.setCar(car);
        tuesdayRentPrice = new RentPrice();
        tuesdayRentPrice.setDayOfWeek(DayOfWeek.TUESDAY);
        tuesdayRentPrice.setPrice(BigDecimal.valueOf(2));
        tuesdayRentPrice.setCar(car);
        wednesdayRentPrice = new RentPrice();
        wednesdayRentPrice.setDayOfWeek(DayOfWeek.WEDNESDAY);
        wednesdayRentPrice.setPrice(BigDecimal.valueOf(3));
        wednesdayRentPrice.setCar(car);
        thursdayRentPrice = new RentPrice();
        thursdayRentPrice.setDayOfWeek(DayOfWeek.THURSDAY);
        thursdayRentPrice.setPrice(BigDecimal.valueOf(4));
        thursdayRentPrice.setCar(car);
        fridayRentPrice = new RentPrice();
        fridayRentPrice.setDayOfWeek(DayOfWeek.FRIDAY);
        fridayRentPrice.setPrice(BigDecimal.valueOf(5));
        fridayRentPrice.setCar(car);
        saturdayRentPrice = new RentPrice();
        saturdayRentPrice.setDayOfWeek(DayOfWeek.SATURDAY);
        saturdayRentPrice.setPrice(BigDecimal.valueOf(6));
        saturdayRentPrice.setCar(car);
        sundayRentPrice = new RentPrice();
        sundayRentPrice.setDayOfWeek(DayOfWeek.SUNDAY);
        sundayRentPrice.setPrice(BigDecimal.valueOf(7));
        sundayRentPrice.setCar(car);
        startDate = LocalDate.of(2023, 1, 1);
        endDate = LocalDate.of(2023, 1, 10);
    }

    @Test
    public void shouldCalculateRentCost() {
        //arrange
        car.setRentPrices(List.of(mondayRentPrice, tuesdayRentPrice, wednesdayRentPrice, thursdayRentPrice, fridayRentPrice, saturdayRentPrice, sundayRentPrice));

        //act
        final var result = rentCalculator.calculateRentCost(startDate, endDate, car);

        //assert
        assertThat(result).isEqualTo(BigDecimal.valueOf(38));
    }

    @Test
    public void shouldThrowException_when_notFoundPriceForDayOfWeek() {
        //arrange
        car.setExternalId("externalId");
        car.setRentPrices(List.of(mondayRentPrice, tuesdayRentPrice, wednesdayRentPrice, thursdayRentPrice, fridayRentPrice, saturdayRentPrice));

        //act
        //assert
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> rentCalculator.calculateRentCost(startDate, endDate, car))
                .withMessage("The SUNDAY rent price for car with external id = externalId not found!");
    }
}
