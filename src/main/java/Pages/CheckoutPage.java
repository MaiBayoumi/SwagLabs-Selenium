package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private final WebDriver driver;
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By zipCodeField = By.id("postal-code");
    private final By cancelButton = By.id("cancel");
    private final By continueBtn = By.id("continue");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public CheckoutPage fillingFormData(String fname, String lname, String zipCode) {
        Utility.sendData(driver, firstNameField, fname);
        Utility.sendData(driver, lastNameField, lname);
        Utility.sendData(driver, zipCodeField, zipCode);
        return this;

    }

    public OverViewPage clickOnContinue() {
        Utility.clickOnElement(driver, continueBtn);
        return new OverViewPage(driver);
    }

    public void clickOnCancel() {
        Utility.clickOnElement(driver, cancelButton);
        new ProductsPage(driver);
    }


    public String getErrorMessage() {
        try {
            return Utility.getTextData(driver, errorMessage);
        } catch (Exception e) {
            return "";
        }
    }
}

