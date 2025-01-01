package be.ucll.bmi.service.steps;

import be.ucll.bmi.data.Persona;
import be.ucll.bmi.model.Patient;
import be.ucll.bmi.service.ServiceException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RegisterPatientIntegrationSteps extends IntegrationSteps {
    @Given("Martha has chosen to add Tom as a patient")
    public void martha_has_chosen_to_add_tom_as_a_patient() {
        Patient patient = Persona.getPatient("Tom");
        context.setPatient(patient);
    }

    @Given("patient Tom with SSN {word} has already been registered")
    public void patient_tom_with_ssn_has_already_been_registered(String ssn) {
    }

    @When("Martha registers {word} as SSN")
    public void martha_registers_as_ssn(String ssn) {
        Patient patient = context.getPatient();
        patient.setSocialSecurityNumber(ssn);

        try {
            patientService.registerPatient(patient);
        } catch (ServiceException e) {
            context.addError(e.getMessage());
        }
    }

    @Then("Martha should be given an error message explaining that the patient is already registered")
    public void martha_should_be_given_an_error_message_explaining_that_the_patient_is_already_registered() {
        List<String> errors = context.getErrors();

        assertEquals(1, errors.size());
        assertEquals("patient.is.already.registered", errors.get(0));
    }
}
