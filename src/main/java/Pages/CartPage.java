package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class CartPage {
    static float totalPrice = 0;
    private final WebDriver driver;
    private final By selectedProductsPrices = By.xpath("(//button[.=\"Remove\"]//preceding-sibling::div[@class='inventory_item_price'])");
    private final By checkOutBTN = By.id("checkout");
    private final By continueShoppingBTN = By.id("continue-shopping");
    private final By removeButtons = By.xpath("//button[text()='Remove']");
    private final By cartItems = By.className("cart_item");
    private final By firstProductNameInCart = By.cssSelector(".inventory_item_name");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPrices() {
        try {
            List<WebElement> productsPrices = driver.findElements(selectedProductsPrices);
            for (int i = 1; i <= productsPrices.size(); i++) {
                By elements = By.xpath("(//button[.=\"Remove\"]//preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                String price = Utility.getTextData(driver, elements);
                totalPrice += Float.parseFloat(price.replace("$", ""));
            }
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            return "0";
        }
    }

    public boolean comparingBothPrices(String price) {
        return getTotalPrices().equals(price);
    }


    public ProductsPage clickOnContinueShoppingBTN() {
        Utility.clickOnElement(driver, continueShoppingBTN);
        return new ProductsPage(driver);
    }


    public void removeItemFromCart() {
        List<WebElement> removeBtns = driver.findElements(removeButtons);
        if (!removeBtns.isEmpty()) {
            int randomIndex = new Random().nextInt(removeBtns.size());
            removeBtns.get(randomIndex).click();
        }
    }

    public int getNumberOfItemsInCart() {
        return driver.findElements(cartItems).size();
    }

    public CheckoutPage clickOnCheckoutBTN() {
        Utility.clickOnElement(driver, checkOutBTN);
        return new CheckoutPage(driver);

    }

    public ProductDetailsPage clickOnFirstProductInCart() {
        Utility.clickOnElement(driver, firstProductNameInCart);
        return new ProductDetailsPage(driver);
    }

}

