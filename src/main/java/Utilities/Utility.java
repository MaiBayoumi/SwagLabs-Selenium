package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;


public class Utility {

    private static final String SCREENSHOTS_PATH = "testOutputs/ScreenShots/";

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

    //Verifies whether the error message is displayed on the page.
    public static boolean isErrorMessageDisplayed(WebDriver driver, By locator) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Returns the text data of the element located by the provided locator.
    public static String getTextData(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    // Generates a random number between 1 and the provided upper bound.
    public static int generateRandomNumber(int boundry) {
        return new Random().nextInt(boundry) + 1;
    }


    // Generates a set of unique random numbers.
    public static List<Integer> generateUniqueNumber(int countToGenerate, int maxAvailableRange) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= maxAvailableRange; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        return numbers.subList(0, Math.min(countToGenerate, numbers.size()));
    }

    public static boolean isElementDisplayed(WebDriver driver, By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public static WebElement toWebElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

    public static File getLatestFile(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            LogsUtils.error("Folder does not exist: " + folderPath + " or is not a directory.");
            return null;
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            LogsUtils.error("No files found in the folder: " + folderPath);
            return null;
        }

        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }


    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd-h-m-ssa").format(new Date());
    }


    public static void takeScreenshot(WebDriver driver, String screenShotName) {
        try {
            File screenShotSRC = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenShotFile = new File(SCREENSHOTS_PATH + screenShotName + "-" + getTimeStamp() + ".png");
            FileUtils.copyFile(screenShotSRC, screenShotFile);
            Allure.addAttachment(screenShotName, Files.newInputStream(screenShotFile.toPath()));
        } catch (IOException e) {
            LogsUtils.error(e.getMessage());
        }
    }

    public static void takeNewScreenshot(WebDriver driver, By locator) {
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL).highlight(toWebElement(driver, locator)).save(SCREENSHOTS_PATH);

        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
        }
    }


}
