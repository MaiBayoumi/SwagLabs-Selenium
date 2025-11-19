package Listeners;

import Utilities.LogsUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

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
}
