package com.bemka.carrental.rent_price.service;

import com.bemka.carrental.car.model.Car;
import com.bemka.carrental.car.service.CarService;
import com.bemka.carrental.common.exception.BadRequestException;
import com.bemka.carrental.common.exception.NotFoundException;
import com.bemka.carrental.rent_price.api.dto.RentPriceDto;
import com.bemka.carrental.rent_price.api.mapper.RentPriceMapper;
import com.bemka.carrental.rent_price.model.RentPrice;
import com.bemka.carrental.rent_price.model.dao.RentPriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentPriceServiceTest {

    @InjectMocks
    private RentPriceService rentPriceService;
    @Mock
    private RentPriceRepository rentPriceRepository;
    @Mock
    private CarService carService;
    @Mock
    private RentPriceMapper rentPriceMapper;

    @Test
    public void shouldAddRentPrice() {
        //arrange
        final var carExternalId = "carExternalId";
        final var rentPriceExternalId = "rentPriceExternalId";
        final var rentPriceDto = new RentPriceDto("monday", BigDecimal.valueOf(20));
        final var car = new Car();
        car.setRentPrices(Collections.emptyList());
        final var rentPrice = new RentPrice();
        rentPrice.setExternalId("rentPriceExternalId");
        when(rentPriceMapper.asEntity(rentPriceDto)).thenReturn(rentPrice);
        when(rentPriceRepository.save(rentPrice)).thenReturn(rentPrice);
        when(carService.getCar(carExternalId)).thenReturn(car);

        //act
        final var result = rentPriceService.addRentPrice(carExternalId, rentPriceDto);

        //assert
        assertThat(result).isEqualTo(rentPriceExternalId);
    }

    @Test
    public void shouldThrowException_when_addRentPriceForDayWithExistingPrice() {
        //arrange
        final var carExternalId = "carExternalId";
        final var rentPriceDto = new RentPriceDto("monday", BigDecimal.valueOf(20));
        final var car = new Car();
        final var rentPrice = new RentPrice();
        final var dayOfWeek = DayOfWeek.MONDAY;
        rentPrice.setDayOfWeek(dayOfWeek);
        car.setRentPrices(List.of(rentPrice));
        when(carService.getCar(carExternalId)).thenReturn(car);

        //act
        //assert
        assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> rentPriceService.addRentPrice(carExternalId, rentPriceDto))
                .withMessage(String.format("The price for %s already exist!", dayOfWeek));
    }

    @Test
    public void shouldGetSortedRentPricesForCar() {
        //arrange
        final var carExternalId = "carExternalId";
        final var car = new Car();
        final var firstRentPrice = new RentPrice();
        firstRentPrice.setDayOfWeek(DayOfWeek.FRIDAY);
        firstRentPrice.setPrice(BigDecimal.valueOf(100));
        firstRentPrice.setCar(car);
        final var secondRentPrice = new RentPrice();
        secondRentPrice.setDayOfWeek(DayOfWeek.MONDAY);
        secondRentPrice.setPrice(BigDecimal.valueOf(20));
        secondRentPrice.setCar(car);
        car.setRentPrices(List.of(firstRentPrice, secondRentPrice));
        final var firstRentPriceDto = new RentPriceDto("friday", BigDecimal.valueOf(100));
        final var secondRentPriceDto = new RentPriceDto("monday", BigDecimal.valueOf(20));
        when(carService.getCar(carExternalId)).thenReturn(car);
        when(rentPriceMapper.asDto(firstRentPrice)).thenReturn(firstRentPriceDto);
        when(rentPriceMapper.asDto(secondRentPrice)).thenReturn(secondRentPriceDto);
        when(rentPriceRepository.getRentPricesByCar(car)).thenReturn(List.of(firstRentPrice, secondRentPrice));
        //act

        final var result = rentPriceService.getRentPricesForCar(carExternalId);
        //assert
        assertThat(result.get(0)).isEqualTo(secondRentPriceDto);
        assertThat(result.get(1)).isEqualTo(firstRentPriceDto);
    }

    @Test
    public void shouldUpdateRentPrice() {
        //arrange
        final var rentPriceExternalId = "rentPriceExternalId";
        final var rentPriceDto = new RentPriceDto("monday", BigDecimal.valueOf(20));
        final var rentPrice = new RentPrice();
        rentPrice.setExternalId("rentPriceExternalId");
        when(rentPriceRepository.findByExternalId(rentPriceExternalId)).thenReturn(Optional.of(rentPrice));
        when(rentPriceRepository.save(rentPrice)).thenReturn(rentPrice);

        //act
        final var result = rentPriceService.updateRentPrice(rentPriceExternalId, rentPriceDto);

        //assert
        assertThat(result).isEqualTo(rentPriceExternalId);
    }

    @Test
    public void shouldThrowException_when_rentPriceNotExist() {
        //arrange
        final var rentPriceExternalId = "rentPriceExternalId";
        final var rentPriceDto = new RentPriceDto("monday", BigDecimal.valueOf(20));
        final var rentPrice = new RentPrice();
        rentPrice.setExternalId("rentPriceExternalId");
        when(rentPriceRepository.findByExternalId(rentPriceExternalId)).thenReturn(Optional.empty());

        //act
        //arrange
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> rentPriceService.updateRentPrice(rentPriceExternalId, rentPriceDto))
                .withMessage(String.format("The rent price with id %s not found!", rentPriceExternalId));
    }
}
