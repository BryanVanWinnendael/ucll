package be.ucll.bmi.model.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateInFutureValidator implements ConstraintValidator<DateInFutureConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintContext) {
        return date == null || !date.isAfter(LocalDate.now());
    }
}