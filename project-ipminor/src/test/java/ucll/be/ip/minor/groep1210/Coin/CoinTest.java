package ucll.be.ip.minor.groep1210.Coin;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ucll.be.ip.minor.groep1210.model.Coin;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import static org.junit.Assert.*;



public class CoinTest {
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
    public void Given_ValidCoin_ShouldNotGiveViolations(){
        Coin coin = CoinBuilder.aValidCoinTestcoin().build();

        Set<ConstraintViolation<Coin>> violations = validator.validate(coin);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void Given_InvalidCoin_WithEmptyName_ShouldGiveViolationNameMissing(){
        Coin coin = CoinBuilder.anInvalidCoinWithNoName().build();

        Set<ConstraintViolation<Coin>> violations = validator.validate(coin);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Coin> violation = violations.iterator().next();
        assertEquals("naam.missing", violation.getMessage());
        assertEquals("naam", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void Given_InvalidCoin_WithEmptyCountry_ShouldGiveViolationCountryMissing(){
        Coin coin = CoinBuilder.anInvalidCoinWithNoCountry().build();

        Set<ConstraintViolation<Coin>> violations = validator.validate(coin);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Coin> violation = violations.iterator().next();
        assertEquals("land.missing", violation.getMessage());
        assertEquals("land", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void Given_InvalidCoin_WithNoCurrency_ShouldGiveViolationCurrencyMissing(){
        Coin coin = CoinBuilder.anInvalidCoinWithNoCurrency().build();

        Set<ConstraintViolation<Coin>> violations = validator.validate(coin);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Coin> violation = violations.iterator().next();
        assertEquals("munteenheid.missing", violation.getMessage());
        assertEquals("munteenheid", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void Given_InvalidCoin_WithNoValue_ShouldGiveViolationValueMissing(){
        Coin coin = CoinBuilder.anInvalidCoinWithNoCurrency().build();

        Set<ConstraintViolation<Coin>> violations = validator.validate(coin);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Coin> violation = violations.iterator().next();
        assertEquals("munteenheid.missing", violation.getMessage());
        assertEquals("munteenheid", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void Given_InvalidCoin_WithValueLessThan0_ShouldGiveViolationValueTooLow(){
        Coin coin = CoinBuilder.anInvalidCoinWithValueLessThan0().build();

        Set<ConstraintViolation<Coin>> violations = validator.validate(coin);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Coin> violation = violations.iterator().next();
        assertEquals("waarde.negative", violation.getMessage());
        assertEquals("waarde", violation.getPropertyPath().toString());
        assertEquals(-1.0, violation.getInvalidValue());
    }

    @Test
    public void Given_InvalidCoin_WithNoYear_ShouldGiveViolationNotANumber(){
        Coin coin = CoinBuilder.anInvalidCointWithNoYear().build();

        Set<ConstraintViolation<Coin>> violations = validator.validate(coin);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Coin> violation = violations.iterator().next();
        assertEquals("jaartal.missing", violation.getMessage());
        assertEquals("jaartal", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }


    @Test
    public void Given_InvalidCoin_WithYearMoreThan2022_ShouldGiveViolationYearTooHigh(){
        Coin coin = CoinBuilder.anInvalidCoinWithYearGreaterThan2022().build();

        Set<ConstraintViolation<Coin>> violations = validator.validate(coin);

        assertEquals(violations.size(), 1);
        ConstraintViolation<Coin> violation = violations.iterator().next();
        assertEquals("jaartal.max", violation.getMessage());
        assertEquals("jaartal", violation.getPropertyPath().toString());
        assertEquals(2050, violation.getInvalidValue());
    }
}
