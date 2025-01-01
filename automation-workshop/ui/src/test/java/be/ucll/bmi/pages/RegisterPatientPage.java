package be.ucll.bmi.pages;

import be.ucll.bmi.model.Gender;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegisterPatientPage extends MessagePage {

    @FindBy(id="socialSecurityNumber")
    private WebElement socialSecurityNumber;

    @FindBy(id="birthDate")
    private WebElement birthDate;

    @FindBy(id="gender")
    private WebElement gender;

    @FindBy(id="examinationDate")
    private WebElement examinationDate;

    @FindBy(id="length")
    private WebElement length;

    @FindBy(id="weight")
    private WebElement weight;

    @FindBy(id="registerPatientButton")
    private WebElement registerPatientButton;

    @Override
    public boolean isOpen(){
        return title.getText().equals(Title.registerPatient);
    }

    public void open(){
        webDriverConfig.goToUrl("/patient/register");
    }

    public void fillInSocialSecurityNumber(String socialSecurityNumber){
        this.socialSecurityNumber.clear();
        this.socialSecurityNumber.sendKeys(socialSecurityNumber);
    }

    public void fillInBirthDate(LocalDate birthDate){
        this.birthDate.clear();
        this.birthDate.sendKeys(birthDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    public void selectGender(Gender gender){
        if(gender != null){
            new Select(this.gender).selectByValue(gender.getValue());
        }
    }

    public void fillInLength(int length){
        fillInLength("" + length);
    }

    public void fillInLength(String length){
        this.length.clear();
        this.length.sendKeys(length);
    }

    public void fillInWeight(int weight){
        fillInWeight("" + weight);
    }

    public void fillInWeight(String weight){
        this.weight.clear();
        this.weight.sendKeys(weight);
    }

    public void fillInExaminationDate(LocalDate examinationDate){
        this.examinationDate.clear();
        this.examinationDate.sendKeys(examinationDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    public void registerPatient(){
        this.registerPatientButton.click();
    }
}
