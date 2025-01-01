package be.ucll.bmi.model;

import be.ucll.bmi.model.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp="^[0-9]*$", message="social.security.number.can.only.contain.digits")
    @SocialSecurityNumberLengthConstraint
    @NotEmpty(message="social.security.number.is.missing")
    @Column(unique=true)
    private String socialSecurityNumber;

    @BirthDateConstraint
    @NotNull(message="birth.date.is.missing")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(message="gender.is.missing")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Valid
    @OneToMany(fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = {CascadeType.ALL})
    private List<Examination> examinations;

    public Patient(){
        examinations = new ArrayList<>();
    }

    public Patient(String socialSecurityNumber, LocalDate birthDate, Gender gender){
        this();
        setSocialSecurityNumber(socialSecurityNumber);
        setBirthDate(birthDate);
        setGender(gender);
    }

    public Patient(String socialSecurityNumber, LocalDate birthDate, Gender gender, Examination examination){
        this();
        setSocialSecurityNumber(socialSecurityNumber);
        setBirthDate(birthDate);
        setGender(gender);
        addExamination(examination);
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void addExamination(Examination examination){
        examinations.add(examination);
    }

    public List<Examination> getExaminations() {
        return examinations;
    }

    public Examination getMostRecentExamination(){
        Collections.sort(examinations);
        return examinations.isEmpty() ? null : examinations.get(examinations.size()-1);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Patient)) return false;

        Patient patient = (Patient)o;
        return getSocialSecurityNumber() == null ? patient.getSocialSecurityNumber() == null : getSocialSecurityNumber().equals(patient.getSocialSecurityNumber());
    }
}
