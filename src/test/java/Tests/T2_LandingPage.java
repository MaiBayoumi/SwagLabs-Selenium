package Tests;

import Pages.LoginPage;
import Pages.ProductsPage;
import Utilities.Utility;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getJsonData;
import static Utilities.DataUtils.getPropertyValue;


public class T2_LandingPage {
    private final String USERNAME = getJsonData("validLoginCredentials", "UserName");
    private final String PASSWORD = getJsonData("validLoginCredentials", "Password");
    private final String BASE_URL = getPropertyValue("environment", "Base_URL");
    private final String CURRENT_BROWSER = getPropertyValue("environment", "Browser");
    private final String CART_URL = getPropertyValue("environment", "Cart_URL");


    @BeforeMethod(alwaysRun = true)
    public void setup() {
        Assert.assertNotNull(CURRENT_BROWSER);
        setupDriver(CURRENT_BROWSER);
        Assert.assertNotNull(BASE_URL);
        getDriver().get(BASE_URL);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }


    @Test(groups = {"regression"})
    public void selectRandomProductTC() {
        new LoginPage(getDriver()).enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin().addRandomProducts(4, 6);
        Assert.assertTrue(new ProductsPage(getDriver()).comparingNumberOfSelectedProductsWithCart());

    }

    @Test(groups = {"regression"})
    public void verifyClickingOnCartTC() {
        new LoginPage(getDriver()).enterUserName(USERNAME)
                .enterPassword(PASSWORD).clickOnLogin()
                .clickingOnCartButton();
        Assert.assertTrue(Utility.verifyUrlRedirection(getDriver(), CART_URL));

    }

    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }
}
