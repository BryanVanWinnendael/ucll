package be.ucll.bmi.model.steps;

import be.ucll.bmi.model.Patient;
import be.ucll.bmi.model.context.UnitTestContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

abstract class UnitSteps {

    @Autowired
    UnitTestContext context;

    void reset(){
        context.reset();
    }

    List<String> validatePatient(Patient patient){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        List<String> errors = new ArrayList<>();
        validator.validate(patient).forEach(e->errors.add(e.getMessage()));

        return errors;
    }
}
