package Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class Utility {
    //Waits until element is clickable, then clicks it.
    public static void clickOnElement(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    //Waits until element is visible, then sends the provided text data to it
    public static void sendData(WebDriver driver, By locator, String data) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(data);
    }

    //Creates WebDriverWait object with a default timeout of 10 seconds.
    public static WebDriverWait general(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Verifies whether the browser has redirected to the expected URL.
    //Waits until the current URL matches the expected one
    public static boolean verifyUrlRedirection(WebDriver driver, String expectedURL) {
        try {
            Utility.general(driver).until(ExpectedConditions.urlToBe(expectedURL));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isErrorMessageDisplayed(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
