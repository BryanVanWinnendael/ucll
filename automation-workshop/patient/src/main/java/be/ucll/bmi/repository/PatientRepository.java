package be.ucll.bmi.repository;

import be.ucll.bmi.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findBySocialSecurityNumber(String socialSecurityNumber);
}
