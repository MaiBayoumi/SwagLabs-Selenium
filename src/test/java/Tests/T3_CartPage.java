package Tests;


import Pages.CartPage;
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


public class T3_CartPage {
    private final String USERNAME = getJsonData("validLoginCredentials", "UserName");
    private final String PASSWORD = getJsonData("validLoginCredentials", "Password");
    private final String BASE_URL = getPropertyValue("environment", "Base_URL");
    private final String CURRENT_BROWSER = getPropertyValue("environment", "Browser");
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
    public void VerifyingCartTotalPriceTC() {
        new LoginPage(getDriver()).enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin().addRandomProducts(3, 6).clickingOnCartButton();
        Assert.assertTrue(new CartPage(getDriver()).comparingBothPrices(new ProductsPage(getDriver()).getTotalPriceForSelectedProducts()));

    }

    @Test
    public void verifyContinueShoppingButtonTC() {
        new LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(1, 3)
                .clickingOnCartButton();

        new CartPage(getDriver()).clickOnContinueShoppingBTN();
        Assert.assertTrue(Utility.verifyUrlRedirection(getDriver(), HOME_URL));
    }

    @Test
    public void verifyRemoveItemFromCartTC() {
        new LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(2, 4)
                .clickingOnCartButton();

        CartPage cartPage = new CartPage(getDriver());
        int beforeRemove = cartPage.getNumberOfItemsInCart();
        Assert.assertTrue(beforeRemove > 0, "Cart should have at least one item before removing!");
        cartPage.removeItemFromCart();
        int afterRemove = cartPage.getNumberOfItemsInCart();
        Assert.assertEquals(afterRemove, beforeRemove - 1, "Item was not removed from cart!");
    }


    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
