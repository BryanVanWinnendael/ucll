package be.ucll.bmi.model;

import be.ucll.bmi.model.constraints.DateInFutureConstraint;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Examination implements Comparable<Examination>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Min(value=30, message="length.should.be.between.30.and.300.cm")
    @Max(value=300, message="length.should.be.between.30.and.300.cm")
    @NotNull(message="length.is.missing")
    private Integer length;

    @Min(value=1000, message="weight.should.be.between.1000.and.300000.gr")
    @Max(value=300000, message="weight.should.be.between.1000.and.300000.gr")
    @NotNull(message="weight.is.missing")
    private Integer weight;

    @NotNull(message="examination.date.is.missing")
    @DateInFutureConstraint
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate examinationDate;

    public Examination(){

    }

    public Examination(Integer length, Integer weight, LocalDate examinationDate){
        setLength(length);
        setWeight(weight);
        setExaminationDate(examinationDate);
    }

    public Integer getLength(){
        return length;
    }

    public void setLength(Integer length){
        this.length = length;
    }

    public Integer getWeight(){
        return weight;
    }

    public void setWeight(Integer weight){
        this.weight = weight;
    }

    public LocalDate getExaminationDate(){
        return examinationDate;
    }

    public void setExaminationDate(LocalDate examinationDate){
        this.examinationDate = examinationDate;
    }

    public Double getBmi(){
        double bmi = 10*getWeight()/Math.pow(getLength(),2);
        return ((double)Math.round(bmi * 10) / 10);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Examination)) return false;

        Examination examination = (Examination)o;

        return Objects.equals(getLength(), examination.getLength()) && Objects.equals(getWeight(), examination.getWeight()) && Objects.equals(getExaminationDate(), examination.getExaminationDate());
    }

    @Override
    public int compareTo(Examination examination) {
        return getExaminationDate().compareTo(examination.getExaminationDate());
    }
}
