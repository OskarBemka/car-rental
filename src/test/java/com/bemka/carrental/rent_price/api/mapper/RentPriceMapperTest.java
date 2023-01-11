package com.bemka.carrental.rent_price.api.mapper;

import com.bemka.carrental.common.exception.BadRequestException;
import com.bemka.carrental.rent_price.api.dto.RentPriceDto;
import com.bemka.carrental.rent_price.model.RentPrice;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.DayOfWeek;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RentPriceMapperTest {

    private final RentPriceMapper rentPriceMapper = Mappers.getMapper(RentPriceMapper.class);

    @Test
    public void shouldMapRentPriceEntityIntoRentPriceDto() {
        //arrange
        final var rentPrice = new RentPrice();
        rentPrice.setDayOfWeek(DayOfWeek.MONDAY);
        rentPrice.setPrice(BigDecimal.valueOf(10));

        //act
        final var result = rentPriceMapper.asDto(rentPrice);

        //assert
        assertThat(result.price()).isEqualTo(rentPrice.getPrice());
        assertThat(result.dayOfWeek()).isEqualTo(rentPrice.getDayOfWeek().toString());
    }

    @Test
    public void shouldMapRentPriceDtoIntoRentPriceEntity() {
        //arrange
        final var rentPriceDto = new RentPriceDto("monday", BigDecimal.valueOf(10));

        //act
        final var result = rentPriceMapper.asEntity(rentPriceDto);

        //assert
        assertThat(result.getPrice()).isEqualTo(rentPriceDto.price());
        assertThat(result.getDayOfWeek().toString()).isEqualToIgnoringCase(rentPriceDto.dayOfWeek());
    }

    @Test
    public void shouldThrowException_when_dayOfWeekIsNull() {
        //arrange
        //act
        //assert
        assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> rentPriceMapper.toDayOfWeek(null))
                .withMessage("The day of week can not be null!");
    }
}
