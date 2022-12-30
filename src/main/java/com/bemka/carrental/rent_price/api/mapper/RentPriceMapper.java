package com.bemka.carrental.rent_price.api.mapper;

import com.bemka.carrental.rent_price.api.dto.RentPriceDto;
import com.bemka.carrental.rent_price.model.RentPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Locale;

@Mapper
@Component
public abstract class RentPriceMapper {

    public abstract RentPriceDto asDto(RentPrice rentPrice);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "car", ignore = true)
    @Mapping(source = "dayOfWeek", target = "dayOfWeek", qualifiedByName = "toDayOfWeek")
    public abstract RentPrice asEntity(RentPriceDto rentPriceDto);

    @Named("toDayOfWeek")
    public DayOfWeek toDayOfWeek(String dayOfWeek) {
        if (dayOfWeek == null) {
            return null;
        }
        return DayOfWeek.valueOf(dayOfWeek.toUpperCase(Locale.ROOT));
    }
}
