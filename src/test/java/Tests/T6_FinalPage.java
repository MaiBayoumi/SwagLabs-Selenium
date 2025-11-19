package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultsListenerClass;
import Pages.FinalPage;
import Pages.LoginPage;
import Utilities.Utility;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.*;

@Listeners({IInvokedMethodListenerClass.class, ITestResultsListenerClass.class})
public class T6_FinalPage {
    private final String USERNAME = getJsonData("validLoginCredentials", "UserName");
    private final String PASSWORD = getJsonData("validLoginCredentials", "Password");
    private final String BASE_URL = getPropertyValue("environment", "Base_URL");
    private final String CURRENT_BROWSER = getPropertyValue("environment", "Browser");
    private final String FIRST_NAME = getJsonData("validCheckOutData", "FirstName");
    private final String LAST_NAME = getJsonData("validCheckOutData", "LastName");
    private final String ZIP_CODE = generateRandomZipCode();
    private final String CURRENT_URL = getPropertyValue("environment", "Home_URL");

    @BeforeMethod
    public void setup() {
        Assert.assertNotNull(CURRENT_BROWSER);
        setupDriver(CURRENT_BROWSER);
        Assert.assertNotNull(BASE_URL);
        getDriver().get(BASE_URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void checkWelcomeMessageTC() {
        new LoginPage(getDriver()).enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin().addRandomProducts(3, 6)
                .clickingOnCartButton()
                .clickOnCheckoutBTN()
                .fillingFormData(FIRST_NAME, LAST_NAME, ZIP_CODE)
                .clickOnContinue()
                .clickOnFinish();

        Assert.assertTrue(new FinalPage(getDriver()).checkMessageVisibility());

        new FinalPage(getDriver()).getBackToHome();
        Assert.assertTrue(Utility.verifyUrlRedirection(getDriver(), CURRENT_URL));
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }

}
