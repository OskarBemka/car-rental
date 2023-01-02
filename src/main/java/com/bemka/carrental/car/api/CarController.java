package com.bemka.carrental.car.api;

import com.bemka.carrental.car.api.dto.CarDto;
import com.bemka.carrental.car.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/car-rental/car")
public class CarController {

    private final CarService carService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String addCar(@RequestBody @Valid CarDto carDto) {
        return carService.addCar(carDto);
    }

    @GetMapping
    public List<CarDto> getCars() {
        return carService.getCars();
    }

    @PutMapping("{externalId}")
    public String updateCar(@RequestBody @Valid CarDto carDto, @PathVariable String externalId) {
        return carService.updateCar(carDto, externalId);
    }
}
