package be.ucll.bmi.ui.steps;

import be.ucll.bmi.data.Persona;
import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Patient;
import be.ucll.bmi.pages.AddExaminationPage;
import be.ucll.bmi.pages.PatientDetailsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddExaminationUISteps extends UISteps {
    @Given("Martha is viewing the patient file of Tom")
    public void martha_is_viewing_the_patient_file_of_tom() {
        PatientDetailsPage patientDetailsPage = new PatientDetailsPage();
        String ssn = Persona.getSsn("Tom");
        patientDetailsPage.open(ssn);

    }

    @When("Martha adds a new examination for Tom")
    public void martha_adds_a_new_examination_for_tom() {
        Examination examination = new Examination(181, 76000, LocalDate.now());
        context.setExamination(examination);

        PatientDetailsPage patientDetailsPage = new PatientDetailsPage();
        AddExaminationPage addExaminationPage = patientDetailsPage.goToAddExamination();

        addExaminationPage.fillInLength(examination.getLength());
        addExaminationPage.fillInWeight(examination.getWeight());
        addExaminationPage.fillInExaminationDate(examination.getExaminationDate());
        addExaminationPage.addExamination();
    }

    @Then("the examination should be added to {word}'s examinations")
    public void the_examination_should_be_added_to_tom_s_examinations(String name) {
        PatientDetailsPage patientDetailsPage = new PatientDetailsPage();
        assertTrue(patientDetailsPage.isOpen());

        Examination examination = context.getExamination();
        assertEquals(examination.getLength(), patientDetailsPage.getLength());
        assertEquals(examination.getWeight(), patientDetailsPage.getWeight());
        assertEquals(examination.getExaminationDate(), patientDetailsPage.getExaminationDate());
    }

    @Then("the BMI should be recalculated")
    public void the_bmi_should_be_recalculated() {
        PatientDetailsPage patientDetailsPage = new PatientDetailsPage();
        assertTrue(patientDetailsPage.isOpen());

        Examination examination = context.getExamination();
        assertEquals(examination.getBmi(), patientDetailsPage.getBmi());
    }
}
