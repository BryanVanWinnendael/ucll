package be.ucll.bmi.service.steps;

import be.ucll.bmi.service.context.IntegrationTestContext;
import be.ucll.bmi.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;

abstract class IntegrationSteps {

    @Autowired
    PatientService patientService;

    @Autowired
    IntegrationTestContext context;

    void reset(){
        context.reset();
    }
}
