package com.bemka.carrental.rent_calculator;

import com.bemka.carrental.car.model.Car;
import com.bemka.carrental.car.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentCalculatorServiceTest {

    @InjectMocks
    private RentCalculatorService rentCalculatorService;
    @Mock
    private CarService carService;
    @Mock
    private RentCalculator rentCalculator;

    @Test
    public void shouldGetRentCost() {
        //arrange
        final var startDate = LocalDate.of(2023, 1, 1);
        final var endDate = LocalDate.of(2023, 1, 10);
        final var externalId = "externalId";
        final var car = new Car();
        final var rentPeriod = new RentPeriod("2023-01-01", "2023-01-10");
        final var expectedValue = BigDecimal.valueOf(10);
        when(carService.getCar(externalId)).thenReturn(car);
        when(rentCalculator.calculateRentCost(startDate, endDate, car)).thenReturn(expectedValue);

        //act
        final var result = rentCalculatorService.getRentCost(externalId, rentPeriod);

        //assert
        assertThat(result).isEqualTo(result);
    }
}
