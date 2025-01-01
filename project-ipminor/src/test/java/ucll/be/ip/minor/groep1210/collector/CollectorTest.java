package ucll.be.ip.minor.groep1210.collector;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ucll.be.ip.minor.groep1210.model.Collector;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import static org.junit.Assert.*;

public class CollectorTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    @Test
    public void givenValidCollector_shouldHaveNoViolations() {

        Collector doja = CollectorBuilder.aCollectorDoja().build();

        Set<ConstraintViolation<Collector>> violations = validator.validate(doja);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void givenCollectorWithEmptyNaam_shouldDetectInvalidNameError() {

        Collector testCollector = CollectorBuilder.anInvalidCollectorWithNoNaam().build();

        Set<ConstraintViolation<Collector>> violations = validator.validate(testCollector);

        assertEquals(violations.size(), 2);
    }

    @Test
    public void givenCollectorWithEmptyVoornaam_shouldDetectInvalidNameError() {

        Collector testCollector = CollectorBuilder.anInvalidCollectorWithNoVoornaam().build();

        Set<ConstraintViolation<Collector>> violations = validator.validate(testCollector);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Collector> violation = violations.iterator().next();
        assertEquals("voornaam.missing", violation.getMessage());
        assertEquals("voornaam", violation.getPropertyPath().toString());
        assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void givenCollectorWithEmptyRegio_shouldDetectInvalidNameError() {

        Collector testCollector = CollectorBuilder.anInvalidCollectorWithNoRegio().build();

        Set<ConstraintViolation<Collector>> violations = validator.validate(testCollector);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Collector> violation = violations.iterator().next();
        assertEquals("regio.missing", violation.getMessage());
        assertEquals("regio", violation.getPropertyPath().toString());
        assertEquals("", violation.getInvalidValue());
    }

    @Test
    public void givenCollectorWithEmptyLeeftijdTooSmall_shouldDetectInvalidNameError() {

        Collector testCollector = CollectorBuilder.anInvalidCollectorWithLeeftijdTooSmall().build();

        Set<ConstraintViolation<Collector>> violations = validator.validate(testCollector);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Collector> violation = violations.iterator().next();
        assertEquals("leeftijd.min", violation.getMessage());
        assertEquals("leeftijd", violation.getPropertyPath().toString());
        assertEquals(15L, violation.getInvalidValue());
    }

    @Test
    public void givenCollectorWithEmptyLeeftijdTooBig_shouldDetectInvalidNameError() {

        Collector testCollector = CollectorBuilder.anInvalidCollectorWithLeeftijdTooBig().build();

        Set<ConstraintViolation<Collector>> violations = validator.validate(testCollector);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Collector> violation = violations.iterator().next();
        assertEquals("leeftijd.max", violation.getMessage());
        assertEquals("leeftijd", violation.getPropertyPath().toString());
        assertEquals(110L, violation.getInvalidValue());
    }

    @Test
    public void givenCollectorWithEmptyNaamTooShort_shouldDetectInvalidNameError() {

        Collector testCollector = CollectorBuilder.anInvalidCollectorWithNaamTooShort().build();

        Set<ConstraintViolation<Collector>> violations = validator.validate(testCollector);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Collector> violation = violations.iterator().next();
        assertEquals("naam.min", violation.getMessage());
        assertEquals("naam", violation.getPropertyPath().toString());
        assertEquals("az", violation.getInvalidValue());
    }
}
