package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage {
    static float totalPrice = 0;
    private final By numberOfProductsOnCartIcon = By.className("shopping_cart_badge");
    private final By numberOfSelectedProducts = By.xpath("//button[.='Remove']");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By selectedProductsPrices = By.xpath("(//button[.=\"Remove\"]//preceding-sibling::div[@class='inventory_item_price'])");
    private final By filterDropdown = By.className("product_sort_container");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
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

    public boolean comparingNumberOfSelectedProductsWithCart() {
        return getNumberOfProductsOnCartIcon().equals(getNumberOfSelectedProducts());

    }

    // Adds random products to the cart based on the provided count and range.
    public ProductsPage addRandomProducts(int countToGenerate, int maxAvailableRange) {
        List<Integer> randomNumbers = Utility.generateUniqueNumber(countToGenerate, maxAvailableRange);
        for (int random : randomNumbers) {
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + random + "]");
            Utility.clickOnElement(driver, addToCartButtonForAllProducts);
        }
        return this;
    }


    public void removeRandomProductFromInventory() {
        List<WebElement> removeButtons = driver.findElements(numberOfSelectedProducts);
        if (!removeButtons.isEmpty()) {
            int beforeRemoveCount = removeButtons.size();
            int randomIndex = new java.util.Random().nextInt(beforeRemoveCount);
            removeButtons.get(randomIndex).click();
        }
    }


    public CartPage clickingOnCartButton() {
        Utility.clickOnElement(driver, cartIcon);
        return new CartPage(driver);
    }

    //Select a filter
    public void selectFilter(String visibleText) {
        WebElement dropdown = driver.findElement(filterDropdown);
        new org.openqa.selenium.support.ui.Select(dropdown).selectByVisibleText(visibleText);
    }

    //(A → Z)
    public boolean isSortedByNameAscending() {
        List<WebElement> items = driver.findElements(productNames);
        List<String> names = items.stream().map(WebElement::getText).toList();
        List<String> sorted = new java.util.ArrayList<>(names);
        java.util.Collections.sort(sorted);
        return names.equals(sorted);
    }

    //(Z → A)
    public boolean isSortedByNameDescending() {
        List<WebElement> items = driver.findElements(productNames);
        List<String> names = items.stream().map(WebElement::getText).toList();
        List<String> sorted = new java.util.ArrayList<>(names);
        java.util.Collections.sort(sorted, java.util.Collections.reverseOrder());
        return names.equals(sorted);
    }

    //(low → high)
    public boolean isSortedByPriceAscending() {
        List<WebElement> items = driver.findElements(productPrices);
        List<Double> prices = items.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
        List<Double> sorted = new java.util.ArrayList<>(prices);
        java.util.Collections.sort(sorted);
        return prices.equals(sorted);
    }

    //(high → low)
    public boolean isSortedByPriceDescending() {
        List<WebElement> items = driver.findElements(productPrices);
        List<Double> prices = items.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .toList();
        List<Double> sorted = new java.util.ArrayList<>(prices);
        java.util.Collections.sort(sorted, java.util.Collections.reverseOrder());
        return prices.equals(sorted);
    }

    public String getTotalPriceForSelectedProducts() {
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
}

