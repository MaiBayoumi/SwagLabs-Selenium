package DriverFactory;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static Utilities.DataUtils.getPropertyValue;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final String EDGEDRIVER = getPropertyValue("environment", "EdgeDriverPath");


    public static void setupDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "edge": {
                System.setProperty("webdriver.edge.driver", EDGEDRIVER);
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("disable-notifications");
                options.addArguments("disable-popup-blocking");
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driverThreadLocal.set(new EdgeDriver(options));
                break;
            }
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--start-maximized");
                options.addArguments("disable-notifications");
                options.addArguments("disable-popup-blocking");
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driverThreadLocal.set(new FirefoxDriver(options));
                break;
            }
            default: {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("disable-notifications");
                options.addArguments("disable-popup-blocking");
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                driverThreadLocal.set(new ChromeDriver(options));
                break;
            }

        }

    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();

    }

    public static void quitDriver() {
        getDriver().quit();
        driverThreadLocal.remove();
    }


}
