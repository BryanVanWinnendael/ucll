package be.ucll.bmi.model.context;

import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Gender;
import be.ucll.bmi.model.Patient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UnitTestContext {

    private Patient patient;

    private Examination examination;

    private Integer length, weight;

    private LocalDate date;

    private List<String> errors;

    public void reset(){
        patient = null;

        examination = null;

        length = null;
        weight = null;

        date = null;

        errors = new ArrayList<>();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
