package Listeners;

import Utilities.FileUtils;
import Utilities.LogsUtils;
import Utilities.report.AllureConstants;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class ITestResultsListenerClass implements ITestListener {
    public void onTestStart(ITestResult result) {
        LogsUtils.info("Test case : " + result.getName() + " started");
    }

    public void onTestSuccess(ITestResult result) {
        LogsUtils.info("Test case : " + result.getName() + " passed successfully");

    }

    public void onTestFailure(ITestResult result) {
        LogsUtils.info("Test case : " + result.getName() + " failed");

    }

    public void onTestSkipped(ITestResult result) {
        LogsUtils.info("Test case : " + result.getName() + " skipped");

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
