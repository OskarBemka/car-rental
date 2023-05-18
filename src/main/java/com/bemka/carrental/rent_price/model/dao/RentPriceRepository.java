package com.bemka.carrental.rent_price.model.dao;

import com.bemka.carrental.car.model.Car;
import com.bemka.carrental.rent_price.model.RentPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface RentPriceRepository extends CrudRepository<RentPrice, Integer> {
    List<RentPrice> getRentPricesByCar(Car car);

    Optional<RentPrice> findByExternalId(String rentPriceExternalId);
}
