package be.ucll.bmi.service.steps;

import be.ucll.bmi.data.Persona;
import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Patient;
import be.ucll.bmi.service.ServiceException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.Assert.assertEquals;

public class AddExaminationIntegrationSteps extends IntegrationSteps {
    @Given("Martha has chosen to add a new examination for Tom")
    public void martha_has_chosen_to_add_a_new_examination_for_tom() {
        Patient patient = Persona.getPatient("Tom");
        context.setPatient(patient);

        Examination examination = new Examination(180, 78000, LocalDate.now());
        context.setExamination(examination);
    }

    @Given("today's date is {date}")
    public void today_s_date_is(LocalDate date) {
        context.setDate(date);
    }

    @When("Martha registers the future date {date} as the examination date")
    public void martha_registers_the_future_date_as_the_examination_date(LocalDate futureExaminationDate) {
        long days = DAYS.between(context.getDate(), LocalDate.now());

        futureExaminationDate = futureExaminationDate.plusDays(days);
        Examination examination = context.getExamination();
        examination.setExaminationDate(futureExaminationDate);

        String patientSsn = context.getPatient().getSocialSecurityNumber();

        try {
            patientService.addExamination(patientSsn, examination);
        } catch (ServiceException e) {
            context.addError(e.getMessage());
        }
    }

    @Then("Martha should be given an error message explaining that the examination date can't be in the future")
    public void martha_should_be_given_an_error_message_explaining_that_the_examination_date_can_t_be_in_the_future() {
        List<String> errors = context.getErrors();

        assertEquals(1, errors.size());
        assertEquals("date.cant.be.in.the.future", errors.get(0));
    }
}
