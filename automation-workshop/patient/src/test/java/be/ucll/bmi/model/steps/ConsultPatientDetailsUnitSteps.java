package be.ucll.bmi.model.steps;

import be.ucll.bmi.PatientApplication;
import be.ucll.bmi.data.Persona;
import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Patient;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@CucumberContextConfiguration
@SpringBootTest(classes = PatientApplication.class)
public class ConsultPatientDetailsUnitSteps extends UnitSteps {

    @Before
    public void setUp(){
        reset();
    }

    @Given("Tom's last examined length is {int} cm")
    public void tom_s_last_examined_length_is_cm(Integer int1) {
    }
    @Given("Tom's last examined weight is {int} gr")
    public void tom_s_last_examined_weight_is_gr(Integer int1) {
    }

    @When("Martha requests the patient details of Tom")
    public void martha_requests_the_patient_details_of_tom() {
        Patient patient = Persona.getPatient("Tom");
        context.setPatient(patient);
    }
    @Then("a BMI of {double} should be given")
    public void a_bmi_of_should_be_given(Double expectedBmi) {
        Patient patient = context.getPatient();
        Examination mostRecentExamination = patient.getMostRecentExamination();
        assertEquals(expectedBmi, mostRecentExamination.getBmi());
    }
}
