package be.ucll.bmi.config;

import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Gender;
import be.ucll.bmi.model.Patient;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class TypeRegistry {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @ParameterType(".*")
    public LocalDate date(String text) {
        return LocalDate.parse(text, dateFormatter);
    }

    @DataTableType
    public Patient patientEntry(Map<String, String> entry) {
        String socialSecurityNumber = entry.get("social security number");

        LocalDate birthDate = LocalDate.parse(entry.get("birth date"), dateFormatter);

        Gender gender = Gender.valueOf(entry.get("gender").toUpperCase());

        return new Patient(socialSecurityNumber, birthDate, gender);
    }

    @DataTableType
    public Examination examinationEntry(Map<String, String> entry) {
        Integer length = Integer.parseInt(entry.get("length"));

        Integer weight = Integer.parseInt(entry.get("weight"));

        LocalDate examinationDate = LocalDate.parse(entry.get("examination date"), dateFormatter);

        return new Examination(length, weight, examinationDate);
    }
}