package com.bemka.carrental.rent_price.service;

import com.bemka.carrental.car.model.dao.CarRepository;
import com.bemka.carrental.common.exception.BadRequestException;
import com.bemka.carrental.common.exception.NotFoundException;
import com.bemka.carrental.rent_price.api.dto.RentPriceDto;
import com.bemka.carrental.rent_price.api.mapper.RentPriceMapper;
import com.bemka.carrental.rent_price.model.dao.RentPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentPriceService {

    private final RentPriceRepository rentPriceRepository;
    private final CarRepository carRepository;
    private final RentPriceMapper rentPriceMapper;

    public String addRentPrice(String carExternalId, RentPriceDto rentPriceDto) {
        final var car = carRepository.findByExternalId(carExternalId)
                .orElseThrow(() -> new NotFoundException(String.format("The car with id %s not found!", carExternalId)));

        final var dayOfWeek = DayOfWeek.valueOf(rentPriceDto.dayOfWeek().toUpperCase(Locale.ROOT));

        if (car.getRentPrices().stream().anyMatch(e -> e.getDayOfWeek().equals(dayOfWeek))) {
            throw new BadRequestException(String.format("The price for %s already exist!", dayOfWeek));
        }
        final var rentPrice = rentPriceMapper.asEntity(rentPriceDto);
        rentPrice.setCar(car);
        return rentPriceRepository.save(rentPrice).getExternalId();
    }

    public List<RentPriceDto> getRentPricesForCar(String carExternalId) {
        final var car = carRepository.findByExternalId(carExternalId)
                .orElseThrow(() -> new NotFoundException(String.format("The car with id %s not found!", carExternalId)));
        return rentPriceRepository.getRentPricesByCar(car).stream().map(rentPriceMapper::asDto)
                .sorted(Comparator.comparing(rentPriceDto -> DayOfWeek.valueOf(rentPriceDto.dayOfWeek()))).collect(Collectors.toList());
    }

    public String updateRentPrice(String rentPriceExternalId, RentPriceDto rentPriceDto) {
        final var rentPrice = rentPriceRepository.findByExternalId(rentPriceExternalId)
                .orElseThrow(() -> new NotFoundException(String.format("The rent price with id %s not found!", rentPriceExternalId)));
        rentPrice.setPrice(rentPriceDto.price());
        return rentPriceRepository.save(rentPrice).getExternalId();
    }
}
