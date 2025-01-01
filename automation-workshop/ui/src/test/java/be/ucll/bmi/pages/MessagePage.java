package be.ucll.bmi.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public abstract class MessagePage extends PageObject {

    @FindBy(className="error")
    private List<WebElement> errors;

    @FindBy(className="message")
    private List<WebElement> messages;

    public boolean displaysMessage(String message){
        for(WebElement e : messages){
            if(e.getText().equals(message)){
                return true;
            }
        }

        return false;
    }

    public boolean displaysError(String errorMessage){
        for(WebElement e : errors){
            if(e.getText().equals(errorMessage)){
                return true;
            }
        }

        return false;
    }

    public List<WebElement> getErrors(){
        return errors;
    }
}
