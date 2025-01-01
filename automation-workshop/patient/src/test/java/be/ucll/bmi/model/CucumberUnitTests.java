package be.ucll.bmi.model;

import net.serenitybdd.cucumber.CucumberWithSerenity;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.SpringFactory;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features={"src/test/resources/features"},
        glue={"be/ucll/bmi/model", "be/ucll/bmi/config"},
        tags="@Unit",
        plugin={"json:target/unit-test.json"},
        objectFactory=SpringFactory.class
)
public class CucumberUnitTests {
}
