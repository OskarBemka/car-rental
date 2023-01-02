package com.bemka.carrental.car.service;

import com.bemka.carrental.car.api.dto.CarDto;
import com.bemka.carrental.car.api.mapper.CarMapper;
import com.bemka.carrental.car.model.Car;
import com.bemka.carrental.car.model.CarBrand;
import com.bemka.carrental.car.model.dao.CarRepository;
import com.bemka.carrental.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public String addCar(CarDto carDto) {
        return carRepository.save(carMapper.asEntity(carDto)).getExternalId();
    }

    public List<CarDto> getCars() {
        final List<Car> cars = (List<Car>) carRepository.findAll();
        return cars.stream().map(carMapper::asDto).collect(Collectors.toList());
    }

    public String updateCar(CarDto carDto, String externalId) {
        final var car = carRepository.findByExternalId(externalId)
                .orElseThrow(() -> new NotFoundException(String.format("The car with id %s not found!", externalId)));
        car.setBrand(CarBrand.valueOf(carDto.brand().toUpperCase(Locale.ROOT)));
        car.setModel(carDto.model());
        return carRepository.save(car).getExternalId();
    }
}
