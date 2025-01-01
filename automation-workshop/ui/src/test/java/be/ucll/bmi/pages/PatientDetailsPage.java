package be.ucll.bmi.pages;

import be.ucll.bmi.model.Gender;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;

public class PatientDetailsPage extends MessagePage {

    @FindBy(id="socialSecurityNumber")
    private WebElement socialSecurityNumber;

    @FindBy(id="birthDate")
    private WebElement birthDate;

    @FindBy(id="gender")
    private WebElement gender;

    @FindBy(id="length")
    private WebElement length;

    @FindBy(id="weight")
    private WebElement weight;

    @FindBy(id="bmi")
    private WebElement bmi;

    @FindBy(id="examinationDate")
    private WebElement examinationDate;

    @FindBy(id="addExaminationButton")
    private WebElement goToAddExaminationButton;

    @Override
    public boolean isOpen(){
        return title.getText().equals(Title.viewPatient);
    }

    public void open(String socialSecurityNumber){
        webDriverConfig.goToUrl("/patient/" + socialSecurityNumber);
    }

    public String getSocialSecurityNumber(){
        return socialSecurityNumber.getText();
    }

    public LocalDate getBirthDate(){
        return LocalDate.parse(birthDate.getText());
    }

    public Gender getGender(){
        return Gender.valueOf(gender.getText());
    }

    public Integer getLength(){
        return Integer.parseInt(length.getText().replace(" cm", ""));
    }

    public Integer getWeight(){
        return Integer.parseInt(weight.getText().replace(" g", ""));
    }

    public LocalDate getExaminationDate(){
        return LocalDate.parse(examinationDate.getText());
    }

    public Double getBmi(){
        return Double.parseDouble(bmi.getText());
    }

    public AddExaminationPage goToAddExamination(){
        goToAddExaminationButton.click();

        return new AddExaminationPage();
    }
}
