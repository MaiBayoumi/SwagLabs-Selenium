package Utilities.report;

import Utilities.LogsUtils;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


public class AllureAttachmentManager {
    // attachScreenshot, attachLogs, attachRecords methods would go here
    public static void attachScreenshot(String name, String path) {
        try {
            Path screenshot = Path.of(path);
            if (Files.exists(screenshot)) {
                Allure.addAttachment(name, Files.newInputStream(screenshot));
            } else {
                LogsUtils.error("Screenshot not found: " + path);
            }
        } catch (Exception e) {
            LogsUtils.error("Error attaching screenshot" + e.getMessage());
        }
    }

    public static void attachLogs() {
        try {
            LogManager.shutdown();
            File logFile = new File(LogsUtils.LOGS_PATH + "logs.log");
            ((LoggerContext) LogManager.getContext(false)).reconfigure();
            if (logFile.exists()) {
                Allure.attachment("logs.log", Files.readString(logFile.toPath()));
            }
        } catch (Exception e) {
            LogsUtils.error("Error attaching logs" + e.getMessage());
        }
    }

    public static void attachRecord(String testMethodName) {
        try {
            String videoPath = "testOutputs/ScreenRecords/" + testMethodName + ".mp4";

            LogsUtils.info("Attaching video: " + videoPath);

            File videoFile = new File(videoPath);

            if (videoFile.exists()) {
                Allure.addAttachment(
                        testMethodName + " - Screen Recording",
                        "video/mp4",
                        Files.newInputStream(videoFile.toPath()),
                        ".mp4"
                );
            } else {
                LogsUtils.error("Video file not found: " + videoPath);
            }

        } catch (Exception e) {
            LogsUtils.error("Error attaching video: " + e.getMessage());
        }
    }


}
