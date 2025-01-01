package be.ucll.bmi.service;

import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Patient;
import be.ucll.bmi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient findPatient(String socialSecurityNumber){
        Patient patient =  patientRepository.findBySocialSecurityNumber(socialSecurityNumber);

        if(patient == null) throw new ServiceException("patient.does.not.exist");

        return patient;
    }

    public void registerPatient(Patient patient){
        if(patientRepository.findBySocialSecurityNumber(patient.getSocialSecurityNumber()) != null) throw new ServiceException("patient.is.already.registered");

        patientRepository.save(patient);
    }

    public void addExamination(String socialSecurityNumber, Examination examination){
        Patient patient = findPatient(socialSecurityNumber);

        patient.addExamination(examination);

        try {
            patientRepository.save(patient);

        } catch (ConstraintViolationException e){
            throw new ServiceException(e.getConstraintViolations().iterator().next().getMessage());
        }
    }

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }
}
