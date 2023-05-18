package com.bemka.carrental.rent_price.service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Size(max = 64, message = "Day of week is too long, max 64 signs!")
@NotBlank(message = "Day of week have to contain non-whitespace characters!")
@Documented
@Constraint(validatedBy = DayOfWeekValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DayOfWeekType {
    String message() default "There is no day of week like this!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
