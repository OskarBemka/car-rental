package com.bemka.carrental.car.service.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class CarBrandValidatorTest {

    private CarBrandValidator carBrandValidator;

    @BeforeEach
    public void init() {
        carBrandValidator = new CarBrandValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"fiat", "Fiat", "FIAT", "ford", "Ford", "FORD", "toyota", "Toyota", "TOYOTA", "vw", "Vw", "VW",
            "bmw", "Bmw", "BMW"})
    public void shouldReturnTrue_when_carBrandIsValid(String carBrand) {
        //arrange
        //act
        final var result = carBrandValidator.isValid(carBrand, null);
        //assert
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalse_when_carBrandIsNotValid() {
        //arrange
        final var carBrand = "wrong car brand";
        //act
        final var result = carBrandValidator.isValid(carBrand, null);
        //assert
        assertThat(result).isFalse();
    }
}
