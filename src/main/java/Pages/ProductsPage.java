package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage {
    private final By numberOfProductsOnCartIcon = By.className("shopping_cart_badge");
    private final By numberOfSelectedProducts = By.xpath("//button[.='Remove']");
    private final By cartIcon = By.className("shopping_cart_link");
    private final WebDriver driver;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }


    public String getNumberOfProductsOnCartIcon() {
        try {
            return Utility.getTextData(driver, numberOfProductsOnCartIcon);
        } catch (Exception e) {
            return "0";
        }
    }

    public String getNumberOfSelectedProducts() {
        try {
            List<WebElement> selectedProducts = driver.findElements(numberOfSelectedProducts);
            return String.valueOf(selectedProducts.size());
        } catch (Exception e) {
            return "0";
        }
    }


    // Adds random products to the cart based on the provided count and range.
    public void addRandomProducts(int countToGenerate, int maxAvailableRange) {
        List<Integer> randomNumbers = Utility.generateUniqueNumber(countToGenerate, maxAvailableRange);
        for (int random : randomNumbers) {
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + random + "]");
            Utility.clickOnElement(driver, addToCartButtonForAllProducts);
        }
    }


    public boolean comparingNumberOfSelectedProductsWithCart() {
        return getNumberOfProductsOnCartIcon().equals(getNumberOfSelectedProducts());

    }

    public void clickingOnCartButton() {
        Utility.clickOnElement(driver, cartIcon);
    }
}

