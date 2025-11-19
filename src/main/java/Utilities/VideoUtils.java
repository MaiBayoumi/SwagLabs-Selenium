package Utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoUtils {

    private static CustomScreenRecorder screenRecorder;

    public static void startRecording(String testName) throws IOException, AWTException {
        File folder = new File("testOutputs/ScreenRecords");
        if (!folder.exists()) folder.mkdirs();

        String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = testName + "_" + date;

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
