package com.bemka.carrental.car.api.mapper;

import com.bemka.carrental.car.api.dto.CarDto;
import com.bemka.carrental.car.model.Car;
import com.bemka.carrental.car.model.CarBrand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Mapper
public abstract class CarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "rentPrices", ignore = true)
    @Mapping(source = "brand", target = "brand", qualifiedByName = "toCarBrand")
    public abstract Car asEntity(CarDto carDto);

    public abstract CarDto asDto(Car car);

    @Named("toCarBrand")
    protected CarBrand toCarBrand(String brand) {
        if (brand == null) {
            return null;
        }
        return CarBrand.valueOf(brand.toUpperCase(Locale.ROOT));
    }
}
