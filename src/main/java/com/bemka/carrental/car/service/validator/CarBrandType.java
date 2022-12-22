package com.bemka.carrental.car.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.*;

import java.lang.annotation.*;

@Size(max = 64, message = "Car brand is too long, max 64 signs!")
@NotBlank(message = "Car brand have to contain non-whitespace characters!")
@Documented
@Constraint(validatedBy = CarBrandValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CarBrandType {
    String message() default "There is no car brand like this!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
