package com.example.viagemdestino.validation.constraints;

import com.example.viagemdestino.validation.TripDateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TripDateRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TripDateRange {
    String message() default "End date must be greater than or equal to start date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}