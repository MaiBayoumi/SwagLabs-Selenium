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

    @Test
    public void verifyRemoveItemFromInventoryPageTC() {
        ProductsPage productsPage = new LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(3, 6);
        int beforeRemove = Integer.parseInt(productsPage.getNumberOfProductsOnCartIcon());
        productsPage.removeRandomProductFromInventory();
        int afterRemove = Integer.parseInt(productsPage.getNumberOfProductsOnCartIcon());
        Assert.assertEquals(afterRemove, beforeRemove - 1, "Item was not removed correctly from inventory page!");
    }

    @Test
    public void verifyFilterFunctionalityTC() {
        ProductsPage productsPage = new LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin();

        productsPage.selectFilter("Name (A to Z)");
        Assert.assertTrue(productsPage.isSortedByNameAscending(), "Products are not sorted A to Z!");

        productsPage.selectFilter("Name (Z to A)");
        Assert.assertTrue(productsPage.isSortedByNameDescending(), "Products are not sorted Z to A!");

        productsPage.selectFilter("Price (low to high)");
        Assert.assertTrue(productsPage.isSortedByPriceAscending(), "Products are not sorted low to high!");

        productsPage.selectFilter("Price (high to low)");
        Assert.assertTrue(productsPage.isSortedByPriceDescending(), "Products are not sorted high to low!");
    }


    @AfterMethod(alwaysRun = true)
    public void quit() {
        quitDriver();
    }
}
