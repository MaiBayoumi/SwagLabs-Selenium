package Utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class VideoUtils {

    public static CustomScreenRecorder screenRecorder;

    public static void startRecording(String testName) throws IOException, AWTException {
        File folder = new File(System.getProperty("user.dir") + "/testOutputs/ScreenRecords");
        if (!folder.exists()) folder.mkdirs();

        String fileName = testName + ".mp4";

        GraphicsConfiguration gc = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        screenRecorder = new CustomScreenRecorder(gc, folder, fileName);
        screenRecorder.start();
    }

    public static void stopRecording() throws IOException {
        if (screenRecorder != null) {
            screenRecorder.stop();
        }
    }
}
