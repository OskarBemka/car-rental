package com.bemka.carrental.car.api.mapper;

import com.bemka.carrental.car.api.dto.CarDto;
import com.bemka.carrental.car.model.Car;
import com.bemka.carrental.car.model.CarBrand;
import com.bemka.carrental.common.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CarMapperTest {

    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);

    @Test
    public void shouldMapCarDtoIntoCarEntity() {
        //arrange
        final var carDto = new CarDto("vw", "passat");

        //act
        final var result = carMapper.asEntity(carDto);

        //assert
        assertThat(result.getBrand()).isEqualTo(CarBrand.valueOf(carDto.brand().toUpperCase(Locale.ROOT)));
        assertThat(result.getModel()).isEqualTo(carDto.model());
    }

    @Test
    public void shouldMapCarEntityIntoCarDto() {
        //arrange
        final var car = new Car();
        car.setBrand(CarBrand.VW);
        car.setModel("passat");

        //act
        final var result = carMapper.asDto(car);

        //assert
        assertThat(result.brand()).isEqualTo(car.getBrand().toString());
        assertThat(result.model()).isEqualTo(car.getModel());
    }

    @Test
    public void shouldThrowException_when_carDtoBrandIsNull() {
        //arrange
        final var carDto = new CarDto(null, "passat");

        //act
        //assert
        assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> carMapper.asEntity(carDto))
                .withMessage("The car brand can not be null!");
    }
}
