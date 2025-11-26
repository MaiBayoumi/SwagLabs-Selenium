package Listeners;

import Pages.ProductsPage;
import Utilities.DataUtils;
import Utilities.FileUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import Utilities.report.AllureAttachmentManager;
import Utilities.report.AllureConstants;
import Utilities.report.AllureEnvironmentManager;
import Utilities.report.AllureReportGenerator;
import io.qameta.allure.Allure;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static DriverFactory.DriverFactory.getDriver;

public class IInvokedMethodListenerClass implements IInvokedMethodListener, IExecutionListener {
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
        AllureAttachmentManager.attachLogs();
//        AllureAttachmentManager.attachRecords(testResult.getMethod().getMethodName());
    }

    public void onExecutionStart() {
        LogsUtils.info("Test Execution started");
        cleanTestOutputDirectories();
        LogsUtils.info("Directories cleaned");
        createTestOutputDirectories();
        LogsUtils.info("Directories created");
        DataUtils.loadProperties();
        LogsUtils.info("Properties loaded");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsUtils.info("Allure environment set");
    }

    public void onExecutionFinish() {
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsUtils.info("Test Execution Finished");
    }

    private void cleanTestOutputDirectories() {
        // Implement logic to clean test output directories
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File("testOutputs/ScreenShots"));
        FileUtils.cleanDirectory(new File("testOutputs/ScreenRecords"));
        FileUtils.forceDelete(new File(LogsUtils.LOGS_PATH + "logs.log"));
    }

    private void createTestOutputDirectories() {
        // Implement logic to create test output directories
        FileUtils.createDirectory("testOutputs/ScreenShots");
        FileUtils.createDirectory("testOutputs/ScreenRecords");

    }
}
