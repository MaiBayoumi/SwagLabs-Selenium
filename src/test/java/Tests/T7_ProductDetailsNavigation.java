package Tests;

import Pages.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.*;

public class T7_ProductDetailsNavigation {
    private final String USERNAME = getJsonData("validLoginCredentials", "UserName");
    private final String PASSWORD = getJsonData("validLoginCredentials", "Password");
    private final String BASE_URL = getPropertyValue("environment", "Base_URL");
    private final String CURRENT_BROWSER = getPropertyValue("environment", "Browser");
    private final String FIRST_NAME = getJsonData("validCheckOutData", "FirstName");
    private final String LAST_NAME = getJsonData("validCheckOutData", "LastName");
    private final String ZIP_CODE = generateRandomZipCode();


    @BeforeMethod
    public void setup() {
        Assert.assertNotNull(CURRENT_BROWSER);
        setupDriver(CURRENT_BROWSER);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Assert.assertNotNull(BASE_URL);
        getDriver().get(BASE_URL);
    }

    @Test
    public void verifyProductDetailsFromAllPages_TC() {
        ProductsPage productsPage = new LoginPage(getDriver())
                .enterUserName(USERNAME)
                .enterPassword(PASSWORD)
                .clickOnLogin()
                .addRandomProducts(2, 6);

        ProductDetailsPage detailsFromProducts = productsPage.clickOnFirstProduct();
        boolean isAddVisible = detailsFromProducts.isAddToCartDisplayed();
        boolean isRemoveVisible = detailsFromProducts.isRemoveButtonDisplayed();
        Assert.assertTrue(isAddVisible || isRemoveVisible);
        getDriver().navigate().back();

        CartPage cartPage = productsPage.clickingOnCartButton();
        ProductDetailsPage detailsFromCart = cartPage.clickOnFirstProductInCart();
        Assert.assertTrue(detailsFromCart.getCurrentURL().contains("inventory-item.html"));
        Assert.assertTrue(detailsFromCart.isRemoveButtonDisplayed());
        getDriver().navigate().back();

        OverViewPage overviewPage = cartPage.clickOnCheckoutBTN()
                .fillingFormData(FIRST_NAME, LAST_NAME, ZIP_CODE)
                .clickOnContinue();
        ProductDetailsPage detailsFromOverview = overviewPage.clickOnFirstProductInOverview();
        Assert.assertTrue(detailsFromOverview.getCurrentURL().contains("inventory-item.html"));
    }

    @AfterMethod
    public void quit() {
        quitDriver();
    }
}
