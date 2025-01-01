package be.ucll.bmi.pages;

import be.ucll.bmi.config.WebDriverConfig;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class PageObject {

    @FindBy(id="title")
    protected WebElement title;

    public static WebDriverConfig webDriverConfig;

    public PageObject(){
        PageFactory.initElements(webDriverConfig.getDriver(), this);
    }

    public abstract boolean isOpen();
}