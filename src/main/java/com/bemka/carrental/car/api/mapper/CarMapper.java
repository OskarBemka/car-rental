package com.bemka.carrental.car.api.mapper;

import com.bemka.carrental.car.api.dto.CreateCarDto;
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

    @Mapping(source = "brand", target = "brand", qualifiedByName = "toCarBrand")
    public abstract Car asEntity(CreateCarDto createCarDto);

    @Named("toCarBrand")
    public CarBrand toCarBrand(String brand) {
        if (brand == null) {
            return null;
        }
        return CarBrand.valueOf(brand.toUpperCase(Locale.ROOT));
    }
}
