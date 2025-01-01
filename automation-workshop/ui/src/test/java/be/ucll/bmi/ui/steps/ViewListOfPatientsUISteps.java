package be.ucll.bmi.ui.steps;

import be.ucll.bmi.PatientApplication;
import be.ucll.bmi.model.Patient;
import be.ucll.bmi.pages.PatientsOverviewPage;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.Assert.assertTrue;

@CucumberContextConfiguration
@SpringBootTest(classes=PatientApplication.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewListOfPatientsUISteps extends UISteps {

    @LocalServerPort
    private int port;

    @Value("${chrome.driver.location}")
    private String chromeDriverLocation;

    @Value("${base.url}")
    private String baseUrl;

    @Before
    public void setUp() {
        initializeTest(baseUrl, port, chromeDriverLocation);
    }

    @After
    public void tearDown() {
        tearDownTest();
    }

    @Given("there are registered patients")
    public void there_are_registered_patients() {
        List<Patient> patients = patientRepository.findAll();
        context.setPatients(patients);
    }
    @When("Martha consults the list of patients")
    public void martha_consults_the_list_of_patients() {
        PatientsOverviewPage patientsOverviewPage = new PatientsOverviewPage();
        patientsOverviewPage.open();
    }
    @Then("Martha should get a list of all patients")
    public void martha_should_get_a_list_of_all_patients() {
        PatientsOverviewPage patientsOverviewPage = new PatientsOverviewPage();
        assertTrue(patientsOverviewPage.isOpen());
        List<Patient> patients = context.getPatients();
        assertTrue(patientsOverviewPage.arePatientsDisplayed(patients));
    }

    @Given("there are no registered patients")
    public void there_are_no_registered_patients() {
        patientRepository.deleteAll();
    }
    @Then("Martha should get a message explaining that there are no registered patients yet")
    public void martha_should_get_a_message_explaining_that_there_are_no_registered_patients_yet() {
        PatientsOverviewPage patientsOverviewPage = new PatientsOverviewPage();
        assertTrue(patientsOverviewPage.isOpen());
        String expectedMessage = getMessage("no.patients");
        assertTrue(patientsOverviewPage.displaysMessage(expectedMessage));
    }
}

