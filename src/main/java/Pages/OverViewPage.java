package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OverViewPage {
    private final WebDriver driver;
    private final By finishBTN = By.id("finish");
    private final By priceBeforeTax = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By totalPriceAfterTax = By.className("summary_total_label");
    private final By cancelButton = By.id("cancel");
    private final By firstProductNameInOverview = By.cssSelector(".inventory_item_name");

    public OverViewPage(WebDriver driver) {
        this.driver = driver;
    }

    public Float getSubTotal() {
        return Float.parseFloat(Utility.getTextData(driver, priceBeforeTax).replace("Item total: $", ""));

    }

    public Float getTax() {
        return Float.parseFloat(Utility.getTextData(driver, tax).replace("Tax: $", ""));
    }

    public Float getTotal() {
        return Float.parseFloat(Utility.getTextData(driver, totalPriceAfterTax).replace("Total: $", ""));
    }

    public String calculateTotalPrice() {
        return String.valueOf(getSubTotal() + getTax());
    }

    public boolean verifyTotalPrice() {
        return calculateTotalPrice().equals(String.valueOf(getTotal()));
    }

    public OrderConfirmationPage clickOnFinish() {
        Utility.clickOnElement(driver, finishBTN);
        return new OrderConfirmationPage(driver);
    }

    public void clickOnCancel() {
        Utility.clickOnElement(driver, cancelButton);
        new ProductsPage(driver);
    }

    public ProductDetailsPage clickOnFirstProductInOverview() {
        Utility.clickOnElement(driver, firstProductNameInOverview);
        return new ProductDetailsPage(driver);
    }
}
