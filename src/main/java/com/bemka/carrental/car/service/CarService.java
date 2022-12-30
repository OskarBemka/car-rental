package com.bemka.carrental.car.service;

import com.bemka.carrental.car.api.dto.CreateCarDto;
import com.bemka.carrental.car.api.mapper.CarMapper;
import com.bemka.carrental.car.model.dao.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public String addCar(CreateCarDto createCarDto) {
        return carRepository.save(carMapper.asEntity(createCarDto)).getExternalId();
    }
}
