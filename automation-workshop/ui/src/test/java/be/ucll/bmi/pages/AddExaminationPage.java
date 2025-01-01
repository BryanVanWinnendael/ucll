package be.ucll.bmi.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddExaminationPage extends MessagePage {

    @FindBy(id="length")
    private WebElement length;

    @FindBy(id="weight")
    private WebElement weight;

    @FindBy(id="examinationDate")
    private WebElement examinationDate;

    @FindBy(id="addExaminationButton")
    private WebElement addExaminationButton;

    @Override
    public boolean isOpen(){
        return title.getText().contains(Title.addExamination);
    }

    public void open(String socialSecurityNumber){
        webDriverConfig.goToUrl("/patient-examination/" + socialSecurityNumber);
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

    public void addExamination(){
        addExaminationButton.click();
    }
}
