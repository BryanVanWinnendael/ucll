package be.ucll.bmi.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverConfig {

    private static WebDriver driver;

    private String baseUrl;
    private int port;

    public WebDriverConfig(String baseUrl, int port, String chromeDriverLocation){
        this.baseUrl = baseUrl;
        this.port = port;

        if(driver == null) {
            System.setProperty("webdriver.chrome.driver", chromeDriverLocation);

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--lang=en-GB");
            //chromeOptions.setHeadless(true);

            driver = new ChromeDriver(chromeOptions);
        }
    }

    public void shutdownDriver(){
        if(driver != null){
            driver.quit();
            driver = null;
        }
    }

    public void goToUrl(String url){
        driver.get("http://" + baseUrl + ":" + port + url);
    }

    public WebDriver getDriver(){
        return driver;
    }
}
