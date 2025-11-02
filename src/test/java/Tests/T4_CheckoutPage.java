package Tests;


import Pages.CheckoutPage;
import Pages.LoginPage;
import Utilities.Utility;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.*;


public class T4_CheckoutPage {
    private final String USERNAME = getJsonData("validLoginCredentials", "UserName");
    private final String PASSWORD = getJsonData("validLoginCredentials", "Password");
    private final String BASE_URL = getPropertyValue("environment", "Base_URL");
    private final String CURRENT_BROWSER = getPropertyValue("environment", "Browser");
    private final String FIRST_NAME = getJsonData("validCheckOutData", "FirstName");
    private final String LAST_NAME = getJsonData("validCheckOutData", "LastName");
    private final String ZIP_CODE = generateRandomZipCode();
    private final String CURRENT_URL = getPropertyValue("environment", "OverView_URL");
    private final String CART_URL = getPropertyValue("environment", "Cart_URL");


    @BeforeMethod
    public void setup() {
        Assert.assertNotNull(CURRENT_BROWSER);
        setupDriver(CURRENT_BROWSER);
        Assert.assertNotNull(BASE_URL);
        getDriver().get(BASE_URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void completeCheckOut_STEP_ONE_TC() {
        new LoginPage(getDriver()).enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(2, 6)
                .clickingOnCartButton()
                .clickOnCheckoutBTN()
                .fillingFormData(FIRST_NAME, LAST_NAME, ZIP_CODE)
                .clickOnContinue();

        Assert.assertTrue(Utility.verifyUrlRedirection(getDriver(), CURRENT_URL));
    }

    @Test
    public void verifyCancelButtonRedirectsToInventory_TC() {
        new LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(1, 3)
                .clickingOnCartButton()
                .clickOnCheckoutBTN()
                .clickOnCancel();

        Assert.assertTrue(Utility.verifyUrlRedirection(getDriver(), CART_URL));
    }

    @Test(groups = {"negative"})
    public void verifyErrorWhenAllFieldsEmpty_TC() {
        CheckoutPage checkoutPage = new LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(1, 3)
                .clickingOnCartButton()
                .clickOnCheckoutBTN();

        checkoutPage.clickOnContinue();
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"));
    }

    @Test(groups = {"negative"})
    public void verifyErrorWhenZipCodeMissing_TC() {
        CheckoutPage checkoutPage = new LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(2, 6)
                .clickingOnCartButton()
                .clickOnCheckoutBTN()
                .fillingFormData(FIRST_NAME, LAST_NAME, "");

        checkoutPage.clickOnContinue();
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Postal Code is required"));
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }

}
