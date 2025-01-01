package be.ucll.bmi.data;

import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Gender;
import be.ucll.bmi.model.Patient;

import java.time.LocalDate;

public class Persona {

    public static Patient getPatient(String name){
        Patient patient;
        Examination examination;

        switch (name) {
            case "Tom":
                patient = new Patient("93051822361", LocalDate.of(1993,5,18), Gender.MALE);

                examination = new Examination(184, 76000, LocalDate.of(2020,9,30));

                patient.addExamination(examination);

                return patient;

            case "Sara":
                patient = new Patient("3219876540", LocalDate.now().minusYears(18), Gender.FEMALE);

                examination = new Examination(174, 73000, LocalDate.now());

                patient.addExamination(examination);

                return patient;
        }

        return null;
    }

    public static String getSsn(String name){
        switch (name) {
            case "Tom":
                return "93051822361";

            case "Sara":
                return "3219876540";
        }

        return null;
    }
}
