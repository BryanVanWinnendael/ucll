package be.ucll.bmi.service;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.SpringFactory;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features={"../patient/src/test/resources/features"},
        glue={"be/ucll/bmi/service", "be/ucll/bmi/config"},
        tags="@Integration",
        plugin={"json:target/integration-test.json"},
        objectFactory=SpringFactory.class
)
public class CucumberIntegrationTests {
}
