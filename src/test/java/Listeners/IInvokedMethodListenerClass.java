package Listeners;

import Pages.ProductsPage;
import Utilities.LogsUtils;
import Utilities.Utility;
import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static DriverFactory.DriverFactory.getDriver;

public class IInvokedMethodListenerClass implements IInvokedMethodListener {
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        File logFile = Utility.getLatestFile(LogsUtils.LOGS_PATH);
        try {
            if (logFile != null && logFile.exists()) {
                Allure.addAttachment("Logs.log", Files.readString(Path.of(logFile.getPath())));
            } else {
                LogsUtils.error("No log file found.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogsUtils.info("Test case" + testResult.getName() + "failed");
            Utility.takeScreenshot(getDriver(), testResult.getName());
            Utility.takeNewScreenshot(getDriver(), new ProductsPage(getDriver()).getCartIcon());

        }
    }

}
