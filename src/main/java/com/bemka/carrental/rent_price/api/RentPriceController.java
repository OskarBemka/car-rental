package com.bemka.carrental.rent_price.api;

import com.bemka.carrental.rent_price.api.dto.RentPriceDto;
import com.bemka.carrental.rent_price.service.RentPriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/car-rental/rent-price")
public class RentPriceController {

    private final RentPriceService rentPriceService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{carExternalId}")
    public String addRentPrice(@PathVariable String carExternalId, @Valid @RequestBody RentPriceDto rentPriceDto) {
        return rentPriceService.addRentPrice(carExternalId, rentPriceDto);
    }

    @GetMapping("{carExternalId}")
    public List<RentPriceDto> getRentPricesForCar(@PathVariable String carExternalId) {
        return rentPriceService.getRentPricesForCar(carExternalId);
    }

    @PutMapping("{rentPriceExternalId}")
    public String updateRentPrice(@PathVariable String rentPriceExternalId, @Valid @RequestBody RentPriceDto rentPriceDto) {
        return rentPriceService.updateRentPrice(rentPriceExternalId, rentPriceDto);
    }
}
