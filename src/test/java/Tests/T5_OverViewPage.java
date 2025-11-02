package Tests;


import Pages.LoginPage;
import Pages.OverViewPage;
import Utilities.Utility;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.*;

public class T5_OverViewPage {
    private final String USERNAME = getJsonData("validLoginCredentials", "UserName");
    private final String PASSWORD = getJsonData("validLoginCredentials", "Password");
    private final String BASE_URL = getPropertyValue("environment", "Base_URL");
    private final String CURRENT_BROWSER = getPropertyValue("environment", "Browser");
    private final String FIRST_NAME = getJsonData("validCheckOutData", "FirstName");
    private final String LAST_NAME = getJsonData("validCheckOutData", "LastName");
    private final String ZIP_CODE = generateRandomZipCode();
    private final String CURRENT_URL = getPropertyValue("environment", "OrderConfirmation_URL");
    private final String HOME_URL = getPropertyValue("environment", "Home_URL");


    @BeforeMethod
    public void setup() {
        Assert.assertNotNull(CURRENT_BROWSER);
        setupDriver(CURRENT_BROWSER);
        Assert.assertNotNull(BASE_URL);
        getDriver().get(BASE_URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void validateTotalPriceTC() {
        new LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(4, 6)
                .clickingOnCartButton()
                .clickOnCheckoutBTN()
                .fillingFormData(FIRST_NAME, LAST_NAME, ZIP_CODE)
                .clickOnContinue();
        
        Assert.assertTrue(new OverViewPage(getDriver()).verifyTotalPrice());

        new OverViewPage(getDriver()).clickOnFinish();
        Assert.assertTrue(Utility.verifyUrlRedirection(getDriver(), CURRENT_URL));
    }

    @Test(groups = {"negative"})
    public void verifyCancelButtonRedirectsToInventory_TC() {
        new LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(1, 3)
                .clickingOnCartButton()
                .clickOnCheckoutBTN()
                .fillingFormData(FIRST_NAME, LAST_NAME, ZIP_CODE)
                .clickOnContinue()
                .clickOnCancel();

        Assert.assertTrue(Utility.verifyUrlRedirection(getDriver(), HOME_URL));
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
