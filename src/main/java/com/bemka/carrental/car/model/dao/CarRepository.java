package com.bemka.carrental.car.model.dao;

import com.bemka.carrental.car.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
}
