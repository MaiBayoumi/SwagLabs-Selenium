package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage {
    private final WebDriver driver;

    private final By addToCartBtn = By.cssSelector("button.btn_primary.btn_inventory");
    private final By removeBtn = By.cssSelector("button.btn_secondary.btn_inventory");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isAddToCartDisplayed() {
        return Utility.isElementDisplayed(driver, addToCartBtn);
    }

    public boolean isRemoveButtonDisplayed() {
        return Utility.isElementDisplayed(driver, removeBtn);
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}
