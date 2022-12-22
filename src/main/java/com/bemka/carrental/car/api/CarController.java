package com.bemka.carrental.car.api;

import com.bemka.carrental.car.api.dto.CreateCarDto;
import com.bemka.carrental.car.api.mapper.CarMapper;
import com.bemka.carrental.car.service.CarService;
import com.bemka.carrental.common.exception.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/car-rental/car")
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String addCar(@RequestBody @Valid CreateCarDto createCarDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        return carService.addCar(carMapper.asEntity(createCarDto));
    }
}
