package com.example.viagemdestino.validation;

import com.example.viagemdestino.models.TripModel;
import com.example.viagemdestino.validation.constraints.TripDateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TripDateRangeValidator implements ConstraintValidator<TripDateRange, TripModel> {

    @Override
    public void initialize(TripDateRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(TripModel tripModel, ConstraintValidatorContext constraintValidatorContext) {
        if (tripModel.getStartDate() == null || tripModel.getEndDate() == null) {
            return false;
        }

        return tripModel.getEndDate().isAfter(tripModel.getStartDate());
    }
}