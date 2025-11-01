package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;
    private final By userNameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginBtn = By.id("login-button");
    private final By errormsg = By.className("error-button");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage enterUserName(String usrName) {
        Utility.sendData(driver, userNameField, usrName);
        return this;
    }

    public LoginPage enterPassword(String passwd) {
        Utility.sendData(driver, passwordField, passwd);
        return this;
    }

    public void clickOnLogin() {
        Utility.clickOnElement(driver, loginBtn);
    }

    public By getErrorMsgLocator() {
        return errormsg;
    }


}
