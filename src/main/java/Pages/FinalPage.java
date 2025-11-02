package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.Utility.toWebElement;

public class FinalPage {
    private final WebDriver driver;
    private final By backToHomeButton = By.id("back-to-products");
    private final By thankYouMessage = By.tagName("h2");

    public FinalPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean checkMessageVisibility() {
        return toWebElement(driver, thankYouMessage).isDisplayed();
    }

    public FinalPage getBackToHome() {
        Utility.clickOnElement(driver, backToHomeButton);
        return this;
    }
}
