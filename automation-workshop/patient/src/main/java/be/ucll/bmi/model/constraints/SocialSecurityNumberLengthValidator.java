package be.ucll.bmi.model.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SocialSecurityNumberLengthValidator implements ConstraintValidator<SocialSecurityNumberLengthConstraint, String> {

    @Override
    public boolean isValid(String socialSecurityNumber, ConstraintValidatorContext constraintContext) {
        return socialSecurityNumber == null || !socialSecurityNumber.matches("^[0-9]+$") || (socialSecurityNumber.length() >= 9 && socialSecurityNumber.length() <= 13);
    }
}