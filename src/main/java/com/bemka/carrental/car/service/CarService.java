package com.bemka.carrental.car.service;

import com.bemka.carrental.car.model.Car;
import com.bemka.carrental.car.model.dao.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public String addCar(Car car) {
        return carRepository.save(car).getExternalId();
    }
}
