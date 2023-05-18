package com.bemka.carrental.car.service;

import com.bemka.carrental.car.api.dto.CarDto;
import com.bemka.carrental.car.api.mapper.CarMapper;
import com.bemka.carrental.car.model.Car;
import com.bemka.carrental.car.model.CarBrand;
import com.bemka.carrental.car.model.dao.CarRepository;
import com.bemka.carrental.common.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @InjectMocks
    private CarService carService;
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarMapper carMapper;

    @Test
    public void shouldAddCar() {
        //arrange
        final var carDto = new CarDto(null, null);
        final var car = new Car();
        car.setExternalId("5a434d24-1ae1-4493-bc3b-897df118790d");
        when(carMapper.asEntity(carDto)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(car);

        //act
        final var result = carService.addCar(carDto);

        //assert
        assertThat(result).isEqualTo("5a434d24-1ae1-4493-bc3b-897df118790d");
    }

    @Test
    public void shouldReturnCars() {
        //arrange
        final var car = new Car();
        final var carDto = new CarDto(null, null);
        final var carDtoList = List.of(carDto);
        when(carRepository.findAll()).thenReturn(List.of(car));
        when(carMapper.asDto(car)).thenReturn(carDto);

        //act
        final var result = carService.getCars();

        //assert
        assertThat(result).isEqualTo(carDtoList);
    }

    @Test
    public void shouldUpdateCar() {
        //arrange
        final var carDto = new CarDto("vw", "golf");
        final var externalId = "5a434d24-1ae1-4493-bc3b-897df118790d";
        final var car = new Car();
        car.setBrand(CarBrand.VW);
        car.setModel("passat");
        car.setExternalId("5a434d24-1ae1-4493-bc3b-897df118790d");
        when(carRepository.findByExternalId(externalId)).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);

        //act
        final var result = carService.updateCar(carDto, externalId);

        //assert
        assertThat(result).isEqualTo(externalId);
        assertThat(car.getModel()).isEqualTo(carDto.model());
    }

    @Test
    public void shouldThrowNotFoundException_when_carToUpdateNotExist() {
        //arrange
        final var carDto = new CarDto("vw", "golf");
        final var externalId = "5a434d24-1ae1-4493-bc3b-897df118790d";
        when(carRepository.findByExternalId(externalId)).thenReturn(Optional.empty());

        //act
        //assert
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> carService.updateCar(carDto, externalId))
                .withMessage(String.format("The car with id %s not found!", externalId));
    }
}
