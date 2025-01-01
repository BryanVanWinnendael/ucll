package be.ucll.bmi.pages;

import be.ucll.bmi.model.Patient;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PatientsOverviewPage extends MessagePage {

    @FindBy(className="patient")
    private List<WebElement> patientWebElements;

    @Override
    public boolean isOpen(){
        return title.getText().equals(Title.patientsOverview);
    }

    public void open(){
        webDriverConfig.goToUrl("/patients");
    }

    public boolean arePatientsDisplayed(List<Patient> patients){
        boolean displayed = true;

        for(int i = 0; i < patients.size() && displayed; i++){
            displayed = isPatientDisplayed(patients.get(i).getSocialSecurityNumber());
        }

        return displayed;
    }

    public boolean isPatientDisplayed(String ssn){
        for(WebElement element : patientWebElements){
            if(element.getText().contains(ssn)) return true;
        }

        return false;
    }
}
