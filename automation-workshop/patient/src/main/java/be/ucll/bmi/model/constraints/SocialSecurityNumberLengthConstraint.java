package be.ucll.bmi.model.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SocialSecurityNumberLengthValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SocialSecurityNumberLengthConstraint {
    String message() default "social.security.number.must.contain.between.9.and.13.digits";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}