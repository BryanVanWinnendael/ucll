package be.ucll.bmi.ui.steps;

import be.ucll.bmi.data.Persona;
import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Patient;
import be.ucll.bmi.pages.PatientDetailsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConsultPatientDetailsUISteps extends UISteps {
    @Given("patient Tom is registered")
    public void patient_tom_is_registered() {
        Patient patient = Persona.getPatient("Tom");
        context.setPatient(patient);
    }

    @When("Martha requests the patient details of {word}")
    public void martha_requests_the_patient_details_of_tom(String name) {
        PatientDetailsPage patientDetailsPage = new PatientDetailsPage();
        String ssn = Persona.getSsn(name);
        patientDetailsPage.open(ssn);
    }

    @Then("Tom's details should be given")
    public void tom_s_details_should_be_given() {
        PatientDetailsPage patientDetailsPage = new PatientDetailsPage();
        assertTrue(patientDetailsPage.isOpen());

        Patient patient = context.getPatient();
        assertEquals(patient.getSocialSecurityNumber(), patientDetailsPage.getSocialSecurityNumber());
        assertEquals(patient.getBirthDate(), patientDetailsPage.getBirthDate());
        assertEquals(patient.getGender(), patientDetailsPage.getGender());

        Examination examination = patient.getMostRecentExamination();
        assertEquals(examination.getExaminationDate(), patientDetailsPage.getExaminationDate());
        assertEquals(examination.getLength(), patientDetailsPage.getLength());
        assertEquals(examination.getWeight(), patientDetailsPage.getWeight());
    }

    @Then("Tom's BMI from the last examination should be given")
    public void tom_s_bmi_from_the_last_examination_should_be_given() {
        PatientDetailsPage patientDetailsPage = new PatientDetailsPage();
        assertTrue(patientDetailsPage.isOpen());

        Patient patient = context.getPatient();
        Examination examination = patient.getMostRecentExamination();
        assertEquals(examination.getBmi(), patientDetailsPage.getBmi());
    }

    @Given("patient Sara is not registered")
    public void patient_sara_is_not_registered() {
    }

    @Then("Martha should be given an error message explaining that the requested patient does not exist")
    public void martha_should_be_given_an_error_message_explaining_that_the_requested_patient_does_not_exist() {
        PatientDetailsPage patientDetailsPage = new PatientDetailsPage();
        assertTrue(patientDetailsPage.isOpen());

        String message = getMessage("patient.does.not.exist");
        assertTrue(patientDetailsPage.displaysError(message));
    }
}
