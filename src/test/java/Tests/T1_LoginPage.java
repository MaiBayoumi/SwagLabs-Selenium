package Tests;

import Pages.LoginPage;
import Utilities.Utility;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getJsonData;
import static Utilities.DataUtils.getPropertyValue;


public class T1_LoginPage {
    // load environment data from env.properties file
    private final String USERNAME = getJsonData("validLoginCredentials", "UserName");
    private final String PASSWORD = getJsonData("validLoginCredentials", "Password");
    private final String BASE_URL = getPropertyValue("environment", "Base_URL");
    private final String HOME_URL = getPropertyValue("environment", "Home_URL");
    private final String CURRENT_BROWSER = getPropertyValue("environment", "Browser");
    private final String INVALID_USERNAME = getJsonData("invalidLoginCredentials", "UserName");
    private final String INVALID_PASSWORD = getJsonData("invalidLoginCredentials", "Password");


    @BeforeMethod(alwaysRun = true)
    public void setup() {
        Assert.assertNotNull(CURRENT_BROWSER);
        setupDriver(CURRENT_BROWSER);
        Assert.assertNotNull(BASE_URL);
        getDriver().get(BASE_URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test(groups = {"regression"})
    public void validLoginTC() {
        new LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin();
        Assert.assertTrue(Utility.verifyUrlRedirection(getDriver(), HOME_URL));
    }


    @Test(groups = {"negative"})
    public void invalidUsernameTC() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterUserName(INVALID_USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin();

        Assert.assertTrue(
                Utility.isErrorMessageDisplayed(getDriver(), loginPage.getErrorMsgLocator()),
                "Error message not displayed for invalid username."
        );
    }


    @Test(groups = {"negative"})
    public void invalidPasswordTC() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterUserName(USERNAME)
                .enterPassword(INVALID_PASSWORD)
                .clickOnLogin();
        Assert.assertTrue(
                Utility.isErrorMessageDisplayed(getDriver(), loginPage.getErrorMsgLocator()),
                "Error message not displayed for invalid username."
        );
    }


    @Test(groups = {"negative"})
    public void invalidUsernameAndPasswordTC() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterUserName(INVALID_USERNAME)
                .enterPassword(INVALID_PASSWORD)
                .clickOnLogin();
        Assert.assertTrue(
                Utility.isErrorMessageDisplayed(getDriver(), loginPage.getErrorMsgLocator()),
                "Error message not displayed for invalid username."
        );
    }

    @Test(groups = {"negative"})
    public void emptyFieldsTC() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.enterUserName("")
                .enterPassword("")
                .clickOnLogin();
        Assert.assertTrue(
                Utility.isErrorMessageDisplayed(getDriver(), loginPage.getErrorMsgLocator()),
                "Error message not displayed for invalid username."
        );
    }

    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }


}
