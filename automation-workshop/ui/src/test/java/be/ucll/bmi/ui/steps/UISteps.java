package be.ucll.bmi.ui.steps;

import be.ucll.bmi.config.WebDriverConfig;
import be.ucll.bmi.data.Persona;
import be.ucll.bmi.pages.PageObject;
import be.ucll.bmi.repository.PatientRepository;
import be.ucll.bmi.ui.context.UITestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Objects;

abstract class UISteps {

    @Autowired
    UITestContext context;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private MessageSource messageSource;

    void initializeTest(String baseUrl, int port, String chromeDriverLocation) {
        patientRepository.deleteAll();
        patientRepository.save(Objects.requireNonNull(Persona.getPatient("Tom")));

        context.reset();

        PageObject.webDriverConfig = new WebDriverConfig(baseUrl, port, chromeDriverLocation);
    }

    void tearDownTest() {
        PageObject.webDriverConfig.shutdownDriver();
    }

    String getMessage(String key) {
        return messageSource.getMessage(key, new Object[0], new Locale("nl", "BE"));
    }
}