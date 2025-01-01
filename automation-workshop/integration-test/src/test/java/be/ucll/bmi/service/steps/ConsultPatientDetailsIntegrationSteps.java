package be.ucll.bmi.service.steps;

import be.ucll.bmi.PatientApplication;
import be.ucll.bmi.data.Persona;
import be.ucll.bmi.service.ServiceException;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(classes = PatientApplication.class)
public class ConsultPatientDetailsIntegrationSteps extends IntegrationSteps {

    @Before
    public void setUp(){
        reset();
    }

    @Given("patient Sara is not registered")
    public void patient_sara_is_not_registered() {
    }

    @When("Martha requests the patient details of Sara")
    public void martha_requests_the_patient_details_of_sara() {
        String ssn = Persona.getSsn("Sara");

        try {
            patientService.findPatient(ssn);
        } catch (ServiceException e) {
            context.addError(e.getMessage());
        }
    }

    @Then("Martha should be given an error message explaining that the requested patient does not exist")
    public void martha_should_be_given_an_error_message_explaining_that_the_requested_patient_does_not_exist() {
        List<String> errors = context.getErrors();
        assertEquals(1, errors.size());
        assertEquals("patient.does.not.exist", errors.get(0));
    }
}
