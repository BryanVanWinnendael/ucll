package be.ucll.bmi.ui.context;

import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Patient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UITestContext {

    private Patient patient;

    private List<Patient> patients;

    private Examination examination;

    private Integer length, weight;

    private LocalDate examinationDate;

    public void reset(){
        patient = null;
        patients = null;

        examination = null;

        length = null;
        weight = null;
        examinationDate = null;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
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

    public LocalDate getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(LocalDate examinationDate) {
        this.examinationDate = examinationDate;
    }
}
